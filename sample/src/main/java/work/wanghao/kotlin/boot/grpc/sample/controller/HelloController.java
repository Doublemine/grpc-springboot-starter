package work.wanghao.kotlin.boot.grpc.sample.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import work.wanghao.kotlin.boot.grpc.sample.service.GrpcClientService;

/**
 * @author doublemine
 * Created on 2018/01/14 19:48.
 */

@RestController
public class HelloController {

	@Autowired
	private GrpcClientService grpcClientService;

	@Autowired
	private HttpServletRequest httpServletRequest;

	@RequestMapping("/")
	public String printMessage(@RequestParam(defaultValue = "Michael") String name) {
		return grpcClientService.sendMessage(name);
	}
}
