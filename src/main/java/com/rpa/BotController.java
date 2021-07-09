package com.rpa;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rpa.domain.Bot;
import com.rpa.dto.CreateBotRequest;
import com.rpa.dto.CreateBotResponse;
import com.rpa.dto.GetAllBotsResponse;
import com.rpa.dto.GetBotResponse;
import com.rpa.dto.Response;
import com.rpa.service.BotService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BotController {
	
	private final BotService botService;
	
	@PostMapping("api/bot")
	public Response createBot(@RequestBody @Valid CreateBotRequest request) {
		
		Bot bot = new Bot();
		bot.setIp(request.getIp());

		CreateBotResponse data = CreateBotResponse.builder().build();

		Long id = botService.save(bot);			
		data.setId(id);

		return new Response(data);
	}

	@GetMapping("api/bots")
	public Response getAllBots(final Pageable pageable) {

		GetAllBotsResponse data = GetAllBotsResponse.builder().build();
		
		Page<Bot> botPage = botService.findAll(pageable);
		List<Bot> bots = botPage.getContent();
		List<GetBotResponse> botResponses = bots.stream()
				.map(b -> new GetBotResponse(b.getId(), b.getIp(), b.getTime()))
				.collect(Collectors.toList());
		
		data.setBots(botResponses);
		data.setTotalElementNum(botPage.getTotalElements());
		data.setTotalPage(botPage.getTotalPages());

		return new Response(data);
	}

}
