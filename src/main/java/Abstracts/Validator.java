package Abstracts;

@FunctionalInterface
public interface Validator <T> {
    boolean validate(T arg);
}
