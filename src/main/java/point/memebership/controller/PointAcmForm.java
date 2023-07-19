package point.memebership.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointAcmForm {
    private Long skey;
    private int point;
    private int expiration;
    private String customer_phone;
}


