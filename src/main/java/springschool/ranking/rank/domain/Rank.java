package springschool.ranking.rank.domain;

import springschool.ranking.rank.Policy;

public class Rank {
    private Long id;
    private String name;
    private Policy policy;
    private int rank;

    public Rank(Long id, String name, Policy policy, int rank) {
        this.id = id;
        this.name = name;
        this.policy = policy;
        this.rank = rank;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Policy getPolicy() {
        return policy;
    }

    public int getRank() {
        return rank;
    }
}
