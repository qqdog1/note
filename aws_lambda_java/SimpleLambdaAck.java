package name.qd.lambda_call;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

public class SimpleLambdaAck implements RequestStreamHandler {

	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		output.write("lllllaaaammmmbbbbbbdddaaaaa".getBytes());
	}
}
