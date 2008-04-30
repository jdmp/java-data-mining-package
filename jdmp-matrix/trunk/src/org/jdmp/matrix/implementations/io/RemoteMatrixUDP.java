package org.jdmp.matrix.implementations.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.logging.Level;

import org.jdmp.matrix.GenericMatrix;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;

public class RemoteMatrixUDP extends GenericMatrix {
	private static final long serialVersionUID = 3889079475875267966L;

	private static final int BUFFERSIZE = 512;

	private static final int TIMEOUT = 1000;

	private DatagramPacket receivedPacket = null;

	private DatagramSocket socket = null;

	private SocketAddress destination = null;

	public RemoteMatrixUDP(String server, int port) {
		try {
			socket = new DatagramSocket();
			socket.setSoTimeout(TIMEOUT);
			destination = new InetSocketAddress(server, port);
			receivedPacket = new DatagramPacket(new byte[BUFFERSIZE], BUFFERSIZE);
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not connnect to matrix", e);
		}
	}

	public synchronized Iterable<long[]> allCoordinates() {
		return null;
	}

	public synchronized long[] getSize() {
		// TODO: not working
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream(BUFFERSIZE);
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeInt(ServerMatrixUDP.GETDIMENSIONCOUNT);
			// oos.writeInt(dimension);
			oos.flush();
			oos.close();
			DatagramPacket sentPacket = new DatagramPacket(bos.toByteArray(), bos.size(), destination);
			socket.send(sentPacket);
			socket.receive(receivedPacket);
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(receivedPacket.getData()));
			int command = ois.readInt();
			if (command != ServerMatrixUDP.GETDIMENSIONCOUNT) {
				logger.log(Level.WARNING, "could not set value");
			}
			int result = ois.readInt();
			ois.close();
			return null;
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not send packet", e);
			return null;
		}
	}

	public synchronized double getDouble(long... coordinates) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream(BUFFERSIZE);
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeInt(ServerMatrixUDP.GETDOUBLEVALUE);
			oos.writeObject(coordinates);
			oos.flush();
			oos.close();
			DatagramPacket sentPacket = new DatagramPacket(bos.toByteArray(), bos.size(), destination);
			socket.send(sentPacket);
			socket.receive(receivedPacket);
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(receivedPacket.getData()));
			int command = ois.readInt();
			if (command != ServerMatrixUDP.GETDOUBLEVALUE) {
				logger.log(Level.WARNING, "could not get value");
			}
			double result = ois.readDouble();
			ois.close();
			return result;
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not send packet", e);
			return Double.NaN;
		}
	}

	public synchronized void setDouble(double value, long... coordinates) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream(BUFFERSIZE);
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeInt(ServerMatrixUDP.SETDOUBLEVALUE);
			oos.writeObject(coordinates);
			oos.writeDouble(value);
			oos.flush();
			oos.close();
			DatagramPacket sentPacket = new DatagramPacket(bos.toByteArray(), bos.size(), destination);
			socket.send(sentPacket);
			socket.receive(receivedPacket);
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(receivedPacket.getData()));
			int command = ois.readInt();
			ois.close();
			if (command != ServerMatrixUDP.SETDOUBLEVALUE) {
				logger.log(Level.WARNING, "could not set value");
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not send packet", e);
		}
	}

	public static void main(String[] args) throws MatrixException {

		Matrix m = Matrix.zeros(10, 10);
		ServerMatrixUDP sm = new ServerMatrixUDP(m, 19000);

		RemoteMatrixUDP rm = new RemoteMatrixUDP("localhost", 19000);

		rm.setDouble(10.0, 4, 3);
		rm.setDouble(10.0, 3, 3);
		rm.setDouble(10.0, 2, 3);

		System.out.println("m:" + m.getDouble(4, 3));
		System.out.println("server:" + sm.getDouble(4, 3));

		System.out.println(rm.toString());
	}

	public boolean contains(long... coordinates) {
		return false;
	}

	@Override
	public Object getObject(long... coordinates) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setObject(Object o, long... coordinates) {
		// TODO Auto-generated method stub

	}

	public boolean isSparse() {
		return false;
	}

	public EntryType getEntryType() {
		return null;
	}

}
