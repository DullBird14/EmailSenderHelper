package com.dull.bird.email.tool.data.entity;

import java.util.Arrays;
import java.util.List;

/**
 * 发送邮件execl解析对象
 * create by DullBird
 * 2018-04-30
 */
public class ExeclAnalyzeResult {
    private String[] titles;
    private List<List<String>> bodies;

    public ExeclAnalyzeResult(String[] titles, List<List<String>> bodies) {
        this.titles = titles;
        this.bodies = bodies;
    }

    public String[] getTitles() {
        return titles;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public List<List<String>> getBodies() {
        return bodies;
    }

    public void setBodies(List<List<String>> bodies) {
        this.bodies = bodies;
    }

    @Override
    public String toString() {
        return "ExeclAnalyzeResult{" + "titles=" + Arrays.toString(titles) + ", bodies=" + bodies + '}';
    }
}
