package org.jdmp.matrix.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

public abstract class SerializationUtil {

	public static void serialize(Serializable obj, OutputStream outputStream) throws IOException {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(outputStream);
			out.writeObject(obj);
		} catch (IOException ex) {
			throw ex;
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException ex) {
				// ignore close exception
			}
		}
	}

	public static Object deserialize(InputStream inputStream) throws ClassNotFoundException, IOException {
		ObjectInputStream in = null;
		try {
			// stream closed in the finally
			in = new ObjectInputStream(inputStream);
			return in.readObject();
		} catch (ClassNotFoundException ex) {
			throw ex;
		} catch (IOException ex) {
			throw ex;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				// ignore close exception
			}
		}
	}

	public static byte[] serialize(Serializable o) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		serialize(o, bos);
		return bos.toByteArray();
	}

	public static Object deserialize(byte[] data) throws ClassNotFoundException, IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		Object o = deserialize(bis);
		return o;
	}

}
