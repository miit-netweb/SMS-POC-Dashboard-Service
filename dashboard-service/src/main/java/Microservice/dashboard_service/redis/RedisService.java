//package Microservice.dashboard_service.redis;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import Microservice.dashboard_service.entity.EmailStatus;
//
//@Service
//public class RedisService {
//
//	@Autowired
//	private RedisTemplate<String, Object> redisTemplate;
//
//	@Autowired
//	private ObjectMapper objectMapper;
//
//	public <T> void saveToList(String key, Object value) {
//		redisTemplate.opsForList().rightPush(key, value);
//	}
//
////	public List<EmailStatus> getList(String key, Class<?> clazz) {
////		
////		List<EmailStatus> objects = redisTemplate.opsForList().range(key, 0, -1);
////		if (objects == null)
////			return null;
////		return objects.stream().map(object -> objectMapper.convertValue(object, clazz))
////				.collect(Collectors.toList());
////	}
//}
