package com.example.todo.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import org.junit.jupiter.api.Test;

class TimeUtilTest {

  private final TemporalAccessor sampleTest = LocalDateTime.of(2024, 9, 1, 10, 30, 45);

  @Test
  void toYmd_Ymd形式の文字列に変換されること() {
    assertEquals("2024-09-01", TimeUtil.Format.toYmd(sampleTest));
  }

  @Test
  void toYmdhm_Ymdhm形式の文字列に変換されること() {
    assertEquals("2024-09-01 10:30", TimeUtil.Format.toYmdhm(sampleTest));
  }

  @Test
  void toYmdhms_Ymdhms形式の文字列に変換されること() {
    assertEquals("2024-09-01 10:30:45", TimeUtil.Format.toYmdhms(sampleTest));
  }

  @Test
  void toYm_Ym形式の文字列に変換されること() {
    assertEquals("2024-09", TimeUtil.Format.toYm(sampleTest));
  }

  @Test
  void toYmdI_YmdI形式の数値に変換されること() {
    assertEquals(20240901, TimeUtil.Format.toYmdI(sampleTest));
  }

  @Test
  void toYmI_YmI形式の数値に変換されること() {
    assertEquals(202409, TimeUtil.Format.toYmI(sampleTest));
  }
}
