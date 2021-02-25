package org.sample;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Configuration for the application.
 */
@SpringBootApplication
public class Application
{
  /**
   * An entry point for the application.
   *
   * @param args An array of {@link String}s.
   */
  public static void main(String[] args)
  {
    new AnnotationConfigApplicationContext(Application.class);
  }
}
