package com.naveen.contactentrysystem.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.naveen.contactentrysystem.dto.SlackRequest;
import com.slack.api.Slack;
import com.slack.api.webhook.WebhookResponse;

@RestController
@RequestMapping("/api")
public class SlackIntegration {

	// webhook url
	final String URL = "";

	@GetMapping("/slack")
	public String sendMessageToSlackChannel(@RequestBody SlackRequest slackRequest) {

		String responseFinal = "";

		try {
			RestTemplate restTemplate = new RestTemplate();

			SlackRequest res = restTemplate.postForObject(URL, slackRequest, SlackRequest.class);

			System.out.println(res);

		} catch (Exception e) {
			e.printStackTrace();

		}

		return responseFinal;
	}

	@GetMapping("/testslack")
	public String testSendMessage() throws IOException {

		Slack slack = Slack.getInstance();

		String webhookUrl = System.getenv("");

		String payload = "{\"text\":\"Hello, World!\"}";

		WebhookResponse response = slack.send(URL, payload);
		System.out.println(response);
		// WebhookResponse(code=200, message=OK, body=ok)

		return "Working";
	}

}
