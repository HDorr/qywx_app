package com.ziwow.scrmapp.tools.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.caucho.hessian.io.SerializerFactory;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class SerializationUtils {
	final static Logger logger = LoggerFactory
			.getLogger(SerializationUtils.class);
	static Kryo kryo = new Kryo();
	static {
		kryo.setReferences(true);
	}

	public static byte[] kryoSerizlize(Object obj) {
		byte[] buffer = new byte[2048];
		Output output = new Output(buffer);
		try {

			kryo.writeClassAndObject(output, obj);
			return output.toBytes();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return buffer;
	}

	public static Object kryoUnSerizlize(byte[] src) {
		Input input = new Input(src);
		try {
			return kryo.readClassAndObject(input);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return kryo;
	}

	public static byte[] serialize(Object obj) throws IOException {
		if (obj == null)
			throw new NullPointerException();

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		HessianOutput ho = new HessianOutput(os);
		ho.writeObject(obj);
		return os.toByteArray();
	}

	public static Object deserialize(byte[] by) throws IOException {
		if (by == null)
			throw new NullPointerException();

		ByteArrayInputStream is = new ByteArrayInputStream(by);
		HessianInput hi = new HessianInput(is);
		return hi.readObject();
	}

	public static byte[] H2Serialize(Object obj) throws IOException {
		ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream(2048);

		Hessian2Output hessianOutput = new Hessian2Output(byteBuffer);

		hessianOutput.setSerializerFactory(reponseSerializerFactory);

		hessianOutput.writeObject(obj);

		hessianOutput.flush();

		byte[] bytes = byteBuffer.toByteArray();
		return bytes;
	}

	static SerializerFactory reponseSerializerFactory = new SerializerFactory();

	public static Object H2Deserialize(byte[] bytes, Class<?> cls)
			throws IOException {

		ByteArrayInputStream input = new ByteArrayInputStream(bytes);

		Hessian2Input hessianInput = new Hessian2Input(input);

		hessianInput.setSerializerFactory(reponseSerializerFactory);
		Object obj = hessianInput.readObject(cls);
		return obj;
	}
}