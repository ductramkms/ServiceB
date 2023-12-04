package com.example.service_b.service.implementation;

import com.example.service_b.service.DepartmentService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.util.StringUtils;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

  private final MeterRegistry meterRegistry;

  public DepartmentServiceImpl(MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;

    Gauge.builder("departments_count", () -> 10)
        .description("A current number of departments in the company")
        .register(meterRegistry);
  }

  @Override
  @SneakyThrows
  public String all(String title) {
    Tag titleTag = Tag.of("title", StringUtils.isEmpty(title) ? "all" : title);
    Timer.Sample timer = Timer.start(meterRegistry);

    String result = "ZERO by Default";

    if (StringUtils.isEmpty(title)) {
      result = "ALL-DEPARTMENT";
    } else {
      if ("Software".equalsIgnoreCase(title)) {
        result = "Software Engineering Department";
      }
    }

    timer.stop(Timer.builder("service_find_all_departments").description(
        "departments searching timer").tags(List.of(titleTag)).register(meterRegistry));

    return result;
  }

}
