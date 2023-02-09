package test;

import main.Block;
import main.CompositeBlock;
import main.Wall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WallTest {

    private static final String BLOCK_BASE_COLOR = "brown";
    private static final String BLOCK_SECONDARY_COLOR = "aquamarine";
    private static final String BLOCK_BASE_MATERIAL = "wood";
    private Wall wall;


    @BeforeEach
    void init(){
        wall = new Wall();
    }

    @Test
    void shouldReturnCountZeroWhenNoBlocksHasBeenAdded(){
        assertEquals(0, wall.count());
    }

    @Test
    void shouldReturnOneWhenOneBlockAdded(){
        wall.addBlock(createTestBlock(BLOCK_BASE_COLOR,BLOCK_BASE_MATERIAL));

        assertEquals(1, wall.count());

    }

    @Test
    void shouldIncludeCompositeBlockNumOfBlocksInCounter(){
        wall.addBlock(new Block() {
            @Override
            public String getColor() {
                return BLOCK_BASE_COLOR;
            }

            @Override
            public String getMaterial() {
                return null;
            }
        });

        wall.addBlock(createTestCompositeBlock(BLOCK_BASE_COLOR, BLOCK_BASE_MATERIAL,Arrays.asList(
                createTestBlock(BLOCK_BASE_COLOR, BLOCK_BASE_MATERIAL),
                createTestBlock(BLOCK_BASE_COLOR, BLOCK_BASE_MATERIAL)
        )));

        assertEquals(3, wall.count());

    }

    @Test
    void shouldReturnEmptyOptionalWhenNoBlockHasBeenAddedAndSearchedByColor(){
        Optional<Block> foundedBlock = wall.findBlockByColor(BLOCK_BASE_COLOR);
        assertTrue(foundedBlock.isEmpty());

    }

    @Test
    void shouldFoundByColorIfBlockIsAdded(){
        wall.addBlock(createTestBlock(BLOCK_BASE_COLOR,BLOCK_BASE_MATERIAL));

        assertTrue(wall.findBlockByColor(BLOCK_BASE_COLOR).isPresent());
    }

    @Test
    void shouldFoundBlockInCompositeWhenIsAdded(){
        wall.addBlock(createTestCompositeBlock(BLOCK_SECONDARY_COLOR, BLOCK_BASE_MATERIAL, Arrays.asList(
                createTestBlock(BLOCK_SECONDARY_COLOR,BLOCK_BASE_MATERIAL),
                createTestBlock(BLOCK_SECONDARY_COLOR,BLOCK_BASE_MATERIAL)
        )));
        var searchedBlock = wall.findBlockByColor(BLOCK_SECONDARY_COLOR);
        assertTrue(searchedBlock.isPresent());
    }

    @Test
    void shouldReturnEmptyListWhenSearchedByMaterial(){
        var foundedBlock = wall.findBlocksByMaterial(BLOCK_BASE_MATERIAL);
        assertTrue(foundedBlock.isEmpty());

    }

    @Test
    void shouldFoundByMaterialIfBlockIsAdded(){
        wall.addBlock(createTestBlock(BLOCK_BASE_COLOR,BLOCK_BASE_MATERIAL));
        assertTrue(wall.findBlocksByMaterial(BLOCK_BASE_MATERIAL).size() > 0);
    }

    @Test
    void shouldFoundBlocksByMaterialIfCompositeBlockWithBlocksIsAdded(){
        wall.addBlock(createTestCompositeBlock(BLOCK_BASE_COLOR,BLOCK_BASE_MATERIAL,Arrays.asList(
                createTestBlock(BLOCK_BASE_COLOR,BLOCK_BASE_MATERIAL),
                createTestBlock(BLOCK_BASE_COLOR,BLOCK_BASE_MATERIAL),
                createTestBlock(BLOCK_BASE_COLOR,BLOCK_BASE_MATERIAL)
                )));
        assertEquals(3, wall.findBlocksByMaterial(BLOCK_BASE_MATERIAL).size());
    }

    @Test
    void shouldNotFoundByMaterialIfBlockIsAdded(){
        wall.addBlock(createTestBlock(BLOCK_BASE_COLOR,BLOCK_BASE_MATERIAL));

        assertEquals(0, wall.findBlocksByMaterial("Wrong_material").size());
        assertEquals(0, wall.findBlocksByMaterial(null).size());
    }


    private Block createTestBlock(String color, String material){
        return new Block() {
            @Override
            public String getColor() {
                return color;
            }

            @Override
            public String getMaterial() {
                return material;
            }
        };
    }

    private CompositeBlock createTestCompositeBlock(String color, String material, Block block){
        return new CompositeBlock() {
            @Override
            public List<Block> getBlocks() {
                return List.of(block);
            }

            @Override
            public String getColor() {
                return color;
            }

            @Override
            public String getMaterial() {
                return material;
            }
        };
    }

    private CompositeBlock createTestCompositeBlock(String color, String material, List<Block> blocks){
        return new CompositeBlock() {
            @Override
            public List<Block> getBlocks() {
                return blocks;
            }

            @Override
            public String getColor() {
                return color;
            }

            @Override
            public String getMaterial() {
                return material;
            }
        };
    }
}
