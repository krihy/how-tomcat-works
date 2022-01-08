package cn.dream.chapter8.loader;

public interface Reloader {

    void addRepository(String repository);

    String[] findRepositories();

    boolean modified();

}
