/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.IntBinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author Ben Norman
 */
public class Main {

    public static void main(String[] args) {
        List<Entity> entities = new ArrayList<>();
        entities.add(new Entity(1, 0, 0));
        entities.add(new Entity(2, 0, 0));
        entities.add(new Entity(3, 0, 0));
        entities.add(new Orc(4, 1, 1));
        entities.add(new Jelly(5, 1, 0));
        entities.add(new Jelly(6, 1, 0));

        List<Entity> all = EntityManager.getAllOfType(Entity.class, entities);
        List<Entity> orcs = EntityManager.getAllOfType(Orc.class, entities);
        List<Entity> jellys = EntityManager.getAllOfType(Jelly.class, entities);
        System.out.println("all.size = " + all.size());
        System.out.println("orcs.size = " + orcs.size());
        System.out.println("jellys.size = " + jellys.size());

        entities.forEach(EntityManager.update);

        Set<Integer> uniqueHPs = entities.stream().map(Entity::getHP).collect(Collectors.toSet());

        // make array of size one so its "final"
        List<Integer> listDiff = new ArrayList<>();
        entities.stream()
                // get ranks of cards as int vals
                .mapToInt((c)->c.getHP())
                // sort by natural order (I think for ints is ascending)
                .sorted()
                // call reduce with custom behvaior
                .reduce((left,right) ->{
                    // add positive difference to list
                    listDiff.add((left < right) ? right - left : left - right);
                    // return right so we step down the list one by one
                    return right;
                });
        // get op's diff field and check that its one less than hand size
        boolean diffIsOne = listDiff
                .stream()
                .allMatch((i) -> i == 1); //was ==3 but now it works for more than 4 cards
    }
}

class EntityManager {

    static Predicate<Entity> isDead = e -> e.hp <= 0;
    static Predicate<Entity> isOrc = e -> e instanceof Orc;

    static List<Entity> getDead(List<Entity> entities) {
        return entities.stream().filter(isDead).collect(Collectors.toList());
    }

    static Consumer<Entity> update = e -> e.update();

    static <T extends Entity> List<Entity> getAllOfType(Class<T> classType, List<Entity> entities) {
        return entities
                .stream()
                .filter(
                        (e) -> e.getClass().equals(classType))
                .collect(Collectors.toList());
    }
}

class Entity {

    int hp;
    int attack;
    int defense;

    public Entity(int hp, int attack, int defense) {
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
    }

    public int getHP() {
        return hp;
    }

    public void update() {
        // do nothing
    }
}

class Orc extends Entity {

    public Orc(int hp, int attack, int defense) {
        super(hp, attack, defense);
    }

}

class Jelly extends Entity {

    public Jelly(int hp, int attack, int defense) {
        super(hp, attack, defense);
    }

}
