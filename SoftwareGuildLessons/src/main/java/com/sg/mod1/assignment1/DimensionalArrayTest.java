/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod1.assignment1;

/**
 *
 * @author Ben Norman
 *
 * testing the use of a 13 dimensional array based on a post from
 * https://softwareengineering.stackexchange.com/questions/246718/how-does-the-fourth-dimension-work-with-arrays
 */
public class DimensionalArrayTest {

    /* "n physics, we assume each spatial dimension to be infinite, which makes finding space for new dimensions pretty difficult.

When dealing with finite arrays, it's easy to find space.

Imagine a sheet of paper with a grid printed on it; you can write some information in each cell of the grid. That's a 2D array: row and column.

Put several of those sheets of paper in a file folder; that's a 3D array: page, row, and column.

Put several of those folders in a file box. 4D array: folder, page, row, column.

Arrange boxes in a rectangular grid on a wooden pallet. 6D array: box-row, box-column, folder, page, row, column.

Stack more grids of boxes on top of those. 7D array: box-depth, box-row, box-column, folder, page, row, column.

Start cramming pallets into a shipping container: 9D array. (Assuming each stack is as tall as the inside of the container, so you can only get 2 more dimensions here.)

Stack up shipping containers on the deck of a container ship: 12D array.

Your fleet of container ships is now a 13D array." */

    int[] cell = new int[25];
    int[][] gridPaper = new int[25][25]; // column row
    int[][][] fildFolder = new int[25][25][25]; // page + prev
    int[][][][] fileBox = new int[25][25][25][25]; // folder + prev
    int[][][][][][] layerFileBox = new int[25][25][25][25][25][25]; // box-column, box-row + prev
    int[][][][][][][] palletFileBox = new int[25][25][25][25][25][25][25]; // box-depth + prev
    int[][][][][][][][][] shippingContainer = new int[25][25][25][25][25][25][25][25][25]; // container-column, container-row + prev
    int[][][][][][][][][][][][] containerShip = new int[25][25][25][25][25][25][25][25][25][25][25][25]; // stack depth, stack column, stack row
    int[][][][][][][][][][][][][] fleetOfShips = new int[25][25][25][25][25][25][25][25][25][25][25][25][25]; // ship

    // another example, there actually was a game with a fourth (mathematically correct) dimension, adding time would simply be doing animations of the fourD objects
    float[][][][][] fourDimensionalTimeSensitiveData = new float[1][1][1][1][1];

    public static void main(String[] args) {
        DimensionalArrayTest dat = new DimensionalArrayTest();
        FleetOfContainerShips fleet = new FleetOfContainerShips();
        int data
                = fleet. // hack
                // first ship in fleet
                containerShips[0].// hack
                // first container on ship(xyz)
                containers[0][0][0].// hack
                // first pallet in container(x y)
                pallets[0][0].// hack
                // first layer in pallet
                layers[0].// hack
                // first file box in layer(x y)
                fileBox[0][0].// hack
                // first folder in box(0,0)
                folders[0][0].// hack
                // first gridPaper in folder
                gridPaper[0].// hack
                // first cell on grid paper
                grid[0][0].// hack
                // data in first cell
                data;// hack
        // vs
        data = dat.fleetOfShips[0][0][0][0][0][0][0][0][0][0][0][0][0]; // a lot less clear I think
    }
}

// of course this would be silly to implement (and use) so lets take a more OOP approach
class Cell {

    int data;
}
// Cell[] cells = new Cell[25];

class GridPaper {

    Cell[][] grid = new Cell[25][25];
}

class FileFolder {

    GridPaper[] gridPaper = new GridPaper[25];
}

class FileBox {

    FileFolder[][] folders = new FileFolder[25][25];
}

class FileBoxLayer {

    FileBox[][] fileBox = new FileBox[25][25];
}

class FileBoxPallet {

    FileBoxLayer[] layers = new FileBoxLayer[25];
}

class ShippingContainer {
    // asuumed to be as tall as container

    FileBoxPallet[][] pallets = new FileBoxPallet[25][25];
}

class ContainerShip {

    ShippingContainer[][][] containers = new ShippingContainer[25][25][25];
}

class FleetOfContainerShips {

    ContainerShip[] containerShips = new ContainerShip[25];
}
// excluding delegation methods for brevity
// to be fair some of thse would in reality be implemented with array lists and only be one dimension
// sycg as the ContainerShip may not care the xyz of a stack of containers only that they exist on the ship
// although if you filled some sort of "Cargo Ship Enterprise Sim" game you might care for generating levels

// "Cargo Ship Enterprise Sim" is a registered trademark of Silly Games Inc. All rights reserved