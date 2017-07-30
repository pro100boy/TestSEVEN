package com.seven.test.to;


import com.seven.test.HasId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
abstract public class BaseTo implements HasId {
    protected Integer id;
}
