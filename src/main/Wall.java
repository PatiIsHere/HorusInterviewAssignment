package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Wall implements Structure {
    private List<Block> blocks;

    public Wall(){
        this.blocks = new ArrayList<>();
    }

    public void addBlock(Block block){
        this.blocks.add(block);
    }

    @Override
    public Optional<Block> findBlockByColor(String color) {

        if(blocks.isEmpty()){
            return Optional.empty();
        }

        var searchedBlock =  blocks.stream()
                .filter(e -> e.getColor().equals(color))
                .findFirst()
                .get();

        if(searchedBlock instanceof CompositeBlock){
            return ((CompositeBlock) searchedBlock)
                    .getBlocks()
                    .stream()
                    .filter(e -> e.getColor().equals(color))
                    .findAny();
        }

        return Optional.of(searchedBlock);

    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        ArrayList<Block> blockList = new ArrayList<>();

        if(blocks.isEmpty()){
            return blockList;
        }

        blocks.stream()
                .filter(e -> e.getMaterial().equals(material))
                .forEach(e -> {
                    if (e instanceof CompositeBlock) {
                        blockList.addAll(((CompositeBlock) e).getBlocks());
                    } else {
                        blockList.add(e);
                    }
                });
        return blockList;

    }

    @Override
    public int count() {
        if (blocks.isEmpty()){
            return 0;
        }else {
            return blocks.stream()
                    .mapToInt(e -> {
                        if (e instanceof CompositeBlock) {
                            return ((CompositeBlock) e).getBlocks().size();
                        } else {
                            return 1;
                        }
                    })
                    .sum();
        }
    }


}