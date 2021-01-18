package name.qd.ec2_lambda.controller;

import java.nio.ByteBuffer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClient;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;

@RestController
@RequestMapping("/test")
public class TestController {

	@PostMapping
	@RequestMapping("")
	public ResponseEntity<String> callLambda() {
		AWSLambda lambda = AWSLambdaClient.builder().withRegion(Regions.AP_NORTHEAST_1).build();
		InvokeRequest invokeRequest = new InvokeRequest();
		invokeRequest.withFunctionName("simple_ack");
		InvokeResult invokeResult = lambda.invoke(invokeRequest);
		ByteBuffer byteBuffer = invokeResult.getPayload();
		String result = new String(byteBuffer.array());
		
		return ResponseEntity.ok(result);
	}
}
