package com.exoreaction.hazelcast.serializer;

import com.exoreaction.hazelcast.rest.Beer;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import java.io.IOException;

public class BeerSerializer implements StreamSerializer<Beer> {

  @Override
  public void write(ObjectDataOutput out, Beer object) throws IOException {
    out.writeUTF(object.getName());
    out.writeUTF(object.getNumber());
  }

  @Override
  public Beer read(ObjectDataInput in) throws IOException {
    return Beer.builder()
        .name(in.readUTF())
        .number(in.readUTF())
        .build();
  }

  @Override
  public int getTypeId() {
    return 1;
  }
}
