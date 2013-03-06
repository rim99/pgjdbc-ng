package com.impossibl.postgres.system.procs;

import java.io.IOException;

import com.impossibl.postgres.system.Context;
import com.impossibl.postgres.types.Type;
import com.impossibl.postgres.utils.DataInputStream;
import com.impossibl.postgres.utils.DataOutputStream;



public class Chars extends SimpleProcProvider {

	public Chars() {
		super(null, null, new Encoder(), new Decoder(), "char");
	}

	static class Decoder implements Type.BinaryIO.Decoder {

		public Character decode(Type type, DataInputStream stream, Context context) throws IOException {

			int length = stream.readInt();
			if(length == -1) {
				return null;
			}
			else if (length != 1) {
				throw new IOException("invalid length");
			}
			
			return (char) stream.readByte();
		}

	}

	static class Encoder implements Type.BinaryIO.Encoder {

		public void encode(Type type, DataOutputStream stream, Object val, Context context) throws IOException {

			if (val == null) {
				
				stream.writeInt(-1);
			}
			else {
				
				stream.writeInt(1);
				stream.writeByte((byte) ((Character) val).charValue());
			}
			
		}

	}

}
