package kr.ac.skuniv.realestate.domain.entity;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@RedisHash("RegionCode")
public class RegionCode implements Serializable {
    @Id
    String id;
    String value;
}