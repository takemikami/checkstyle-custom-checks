package sample;

import io.swagger.annotations.ApiModelProperty;

public class TargetClassApiModelPropertyFields {

  @ApiModelProperty(value = "first field", example = "1", position = 1)
  public int fieldOne;

  @ApiModelProperty(value = "second field", example = "foobar", position = 2)
  public String fieldTwo;

  public TargetClassApiModelPropertyFields() {
    String x;
  }

}
