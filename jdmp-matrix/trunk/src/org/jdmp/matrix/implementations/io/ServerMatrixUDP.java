package org.jdmp.matrix.implementations.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;

import org.jdmp.matrix.Coordinates;
import org.jdmp.matrix.AbstractGenericMatrix;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;

public class ServerMatrixUDP<A> extends AbstractGenericMatrix<A> {
	private static final long serialVersionUID = 3907994158174208114L;

	private static final int BUFFERSIZE = 512;

	public static final int GETDOUBLEVALUE = 0;

	public static final int SETDOUBLEVALUE = 1;

	public static final int GETDIMENSIONCOUNT = 2;

	private Matrix matrix = null;

	private DatagramSocket socket = null;

	private DatagramPacket receivedPacket = null;

	private ServerThread thread = null;

	public ServerMatrixUDP(Matrix matrix, int port) {
		this.matrix = matrix;

		try {
			receivedPacket = new DatagramPacket(new byte[BUFFERSIZE], BUFFERSIZE);
			thread = new ServerThread();
			socket = new DatagramSocket(port);
			thread.start();
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not open socket", e);
		}
	}

	public Iterable<long[]> allCoordinates() {
		return matrix.allCoordinates();
	}

	public long[] getSize() {
		return matrix.getSize();
	}

	public double getDouble(long... coordinates) throws MatrixException {
		return matrix.getDouble(coordinates);
	}

	public A getObject(long... coordinates) throws MatrixException {
		return (A) matrix.getObject(coordinates);
	}

	@Override
	public long getValueCount() {
		return matrix.getValueCount();
	}

	public boolean isSparse() {
		return matrix.isSparse();
	}

	public void setDouble(double value, long... coordinates) throws MatrixException {
		matrix.setDouble(value, coordinates);
	}

	public void setObject(Object o, long... coordinates) throws MatrixException {
		matrix.setObject(o, coordinates);
	}

	class ServerThread extends Thread {

		public ServerThread() {

		}

		public void run() {

			try {

				while (true) {

					socket.receive(receivedPacket);

					ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(receivedPacket.getData()));
					ByteArrayOutputStream bos = new ByteArrayOutputStream(BUFFERSIZE);
					ObjectOutputStream oos = new ObjectOutputStream(bos);

					int command = ois.readInt();
					Coordinates coordinates = null;
					double value = 0.0;
					switch (command) {
					case SETDOUBLEVALUE:
						coordinates = (Coordinates) ois.readObject();
						value = ois.readDouble();
						setDouble(value, coordinates.dimensions);
						oos.writeInt(SETDOUBLEVALUE);
						break;
					case GETDOUBLEVALUE:
						coordinates = (Coordinates) ois.readObject();
						value = getDouble(coordinates.dimensions);
						oos.writeInt(GETDOUBLEVALUE);
						oos.writeDouble(value);
						break;
					case GETDIMENSIONCOUNT:
						int dimension = ois.readInt();
						int result = (int) getSize(dimension);
						oos.writeInt(GETDIMENSIONCOUNT);
						oos.writeInt(result);
						break;
					}

					oos.flush();

					DatagramPacket sentPacket = new DatagramPacket(bos.toByteArray(), bos.size(), receivedPacket
							.getAddress(), receivedPacket.getPort());
					socket.send(sentPacket);

				}

			} catch (Exception e) {
				logger.log(Level.WARNING, "error in data transmission", e);
			}
		}
	}

	public boolean contains(long... coordinates) {
		return matrix.contains(coordinates);
	}

	@Override
	public boolean isReadOnly() {
		return matrix.isReadOnly();
	}

	public EntryType getEntryType() {
		return matrix.getEntryType();
	}

}
