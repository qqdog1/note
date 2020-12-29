package name.qd.lambda_call;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClient;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

public class SimpleLambdaCaller implements RequestStreamHandler {

	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		AWSLambda lambda = AWSLambdaClient.builder().withRegion(Regions.AP_NORTHEAST_1).build();
		InvokeRequest invokeRequest = new InvokeRequest();
		invokeRequest.withFunctionName(System.getenv("CALL_FUNCTION"));
		InvokeResult invokeResult = lambda.invoke(invokeRequest);
		ByteBuffer byteBuffer = invokeResult.getPayload();		
		output.write(byteBuffer.array());
	}
}
