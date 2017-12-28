package com.hobbygo.api.hobbygoapi.model.entity;

import com.hobbygo.api.hobbygoapi.model.constants.Hobby;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Play {

    public static int DEFAULTID = 9001;

    private String id;
    @DBRef
    private List<Group> groupList;
    @DBRef
    private Group winner;
    private int winnerScore;
    private int loserScore;
    private LocalDateTime date;
    private Hobby hobby;

    private Play(){}

    public Play(List<Group> groups, LocalDateTime date, Hobby hobby){
        setGroupList(new ArrayList<>());
        getGroupList().addAll(groups);
        setDate(date);
        setHobby(hobby);
    }
/*
    public static class Node{
        private Node parent;
        private Node brother;
        private String groupId;
        private List<Node> children;

        public static void link(Node nodeAnt, Node node){
            nodeAnt.setBrother(node);
            node.setBrother(nodeAnt);
        }

        public Node(String groupId, Node parent){
            this.groupId = groupId;
            this.parent = parent;
            this.children = new ArrayList<>();
            if(parent!=null && parent.children.contains(this)==false) {
                parent.children.add(this);
            }
        }

        public String getGroupId() {
            return groupId;
        }

        public Node parent() {
            return parent;
        }

        public List<Node> children() {
            return children;
        }

        public Node getBrother() {
            return brother;
        }

        public void setBrother(Node brother) {
            this.brother = brother;
        }

        @Override
        public String toString() {
            return String.valueOf(groupId);
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Node)){
                return false;
            }
            Node that = (Node) obj;
            return this.groupId == that.groupId && this.parent == that.parent;
        }
    }
*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    public Group getWinner() {
        return winner;
    }

    public void setWinner(Group winner) {
        this.winner = winner;
    }

    public int getWinnerScore() {
        return winnerScore;
    }

    public void setWinnerScore(int winnerScore) {
        this.winnerScore = winnerScore;
    }

    public int getLoserScore() {
        return loserScore;
    }

    public void setLoserScore(int loserScore) {
        this.loserScore = loserScore;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Hobby getHobby() {
        return hobby;
    }

    public void setHobby(Hobby hobby) {
        this.hobby = hobby;
    }

    public Boolean isPlayerScoreable(Player player){

        for(Group group:getGroupList())
            if(group.isPlayerAMember(player))
                if(group.isAutoGen())
                    return true;

        return false;

    }
}
