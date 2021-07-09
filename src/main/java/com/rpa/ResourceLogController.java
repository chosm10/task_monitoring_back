package com.rpa;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rpa.domain.ResourceLog;
import com.rpa.dto.GetAllResourceLogsResponse;
import com.rpa.dto.GetResourceLogResponse;
import com.rpa.dto.ResourceLogRequest;
import com.rpa.dto.Response;
import com.rpa.repository.ResourceLogRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ResourceLogController {
	private final ResourceLogRepository resourceLogRepository;
	/*
	 * postman으로 테스트 할때 크롬 cors플러그인 활성화 하고 포스트맨 접속해야 가능
	 * */
	
	@PostMapping("api/rsc-log")
	public Response getAllResourceLog(final Pageable pageable, @RequestBody @Valid ResourceLogRequest request) {
//		여기 넘어오는 페이지 번호는 원래 번호 -1로 와야함
		String[] s = request.getDate().split("-");
		LocalDateTime start = LocalDateTime.of(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]), 0, 0, 0);
		LocalDateTime end = LocalDateTime.of(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]), 23, 59, 59);
		
		Page<ResourceLog> page = null;
		if(request.getIp() != null) {
			page = resourceLogRepository.findAllByIpAndTimeBetween(request.getIp(),start, end, pageable);			
		} else {
			page = resourceLogRepository.findAllByTimeBetween(start, end, pageable);
		}
		
		GetAllResourceLogsResponse data = GetAllResourceLogsResponse.builder().build();
		List<ResourceLog> logs = page.getContent();
		//			Task도 entity가 아닌 DTO로 맵핑해서 반환
		List<GetResourceLogResponse> resourceLogResponse = logs.stream()
				.map(l -> new GetResourceLogResponse(l.getIp(), l.getCpu(), l.getMemory(), l.getDisk(), l.getErr(), l.getTime()))
				.collect(Collectors.toList());
		
		data.setHistory(resourceLogResponse);
		data.setTotalElementNum(page.getTotalElements());
		data.setTotalPage(page.getTotalPages());
		
		return new Response(data);
	}
}
