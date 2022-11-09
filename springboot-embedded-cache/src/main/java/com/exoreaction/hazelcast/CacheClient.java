package com.exoreaction.hazelcast;

import com.exoreaction.hazelcast.rest.Beer;
import com.exoreaction.hazelcast.serializer.BeerSerializer;
import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.stereotype.Component;

@Component
public class CacheClient {


  public static final String BEERS = "beers";
  private final HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(createConfig());

  public Beer put(String number, Beer beer){
    IMap<String, Beer> map = hazelcastInstance.getMap(BEERS);
    return map.putIfAbsent(number, beer);
  }

  public Beer get(String key){
    IMap<String, Beer> map = hazelcastInstance.getMap(BEERS);
    return map.get(key);
  }

  public Config createConfig() {
    Config config = new Config();
    config.addMapConfig(mapConfig());
    config.getSerializationConfig().addSerializerConfig(serializerConfig());
    return config;
  }

  private SerializerConfig serializerConfig() {
    return  new SerializerConfig()
        .setImplementation(new BeerSerializer())
        .setTypeClass(Beer.class);
  }

  private MapConfig mapConfig() {
    MapConfig mapConfig = new MapConfig(BEERS);
    mapConfig.setTimeToLiveSeconds(360);
    mapConfig.setMaxIdleSeconds(20);
    return mapConfig;
  }
}
