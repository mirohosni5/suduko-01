/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import utils.NotFoundException;
import controller.DifficultyEnum;
import View.UserAction;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author M
 */
public class StorageManager {
    private static final String BASE_DIR = "games";
    private static final String EASY_DIR = BASE_DIR + "/easy";
    private static final String MEDIUM_DIR = BASE_DIR + "/medium";
    private static final String HARD_DIR = BASE_DIR + "/hard";
    private static final String CURRENT_DIR = BASE_DIR + "/incomplete";
    private static final String LOG_FILE = CURRENT_DIR + "/game.log";
    
    public StorageManager() {
        createDirectories();
    }
    
    private void createDirectories() {
        new File(EASY_DIR).mkdirs();
        new File(MEDIUM_DIR).mkdirs();
        new File(HARD_DIR).mkdirs();
        new File(CURRENT_DIR).mkdirs();
    }
    public static void saveBoard (String path, int[][] board) throws FileNotFoundException{
        File f = new File(path);
        f.getParentFile().mkdirs();
        try(PrintWriter p = new PrintWriter(f)){
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    p.print(board[i][j] + " ");
                }
                p.println();
            }
        }
    }
    public static int[][] loadBoard(String path) throws FileNotFoundException, IOException  {
        int[][] board = new int[9][9];

        try (BufferedReader b = new BufferedReader(new FileReader(path))) {
            for (int i = 0; i < 9; i++) {
                String[] line = b.readLine().split(" ");
                for (int j = 0; j < 9; j++) {
                    board[i][j] = Integer.parseInt(line[j]);
                }
            }
        }
        return board;
    }
     public void saveGame(DifficultyEnum level, int[][] board) throws IOException {
        String dir = getDirectory(level);
        String filename = dir + "/game_" + System.currentTimeMillis() + ".txt";
        saveBoard(filename, board);
    }
    
    public void saveCurrentGame(int[][] board) throws IOException {
        String filename = CURRENT_DIR + "/current_game.txt";
        saveBoard(filename, board);
    }
    
    public int[][] loadGame(DifficultyEnum level) throws IOException, NotFoundException {
        String dir = getDirectory(level);
        File folder = new File(dir);
        File[] files = folder.listFiles((d, name) -> name.endsWith(".txt"));
        
        if (files == null || files.length == 0) {
            throw new NotFoundException("No game found for level: " + level);
        }
        
        return loadBoard(files[0].getPath());
    }
    
    public int[][] loadCurrentGame() throws IOException, NotFoundException {
        String filename = CURRENT_DIR + "/current_game.txt";
        File file = new File(filename);
        if (!file.exists()) {
            throw new NotFoundException("No current game found");
        }
        return loadBoard(filename);
    }
    
    public boolean hasCurrentGame() {
        return new File(CURRENT_DIR + "/current_game.txt").exists();
    }
    
    public boolean hasAllDifficulties() {
        return hasGames(EASY_DIR) && hasGames(MEDIUM_DIR) && hasGames(HARD_DIR);
    }
    
    private boolean hasGames(String dir) {
        File folder = new File(dir);
        File[] files = folder.listFiles((d, name) -> name.endsWith(".txt"));
        return files != null && files.length > 0;
    }
    
    public void deleteGame(DifficultyEnum level) {
        String dir = getDirectory(level);
        File folder = new File(dir);
        File[] files = folder.listFiles((d, name) -> name.endsWith(".txt"));
        if (files != null && files.length > 0) {
            files[0].delete();
        }
    }
    
    public void deleteCurrentGame() {
        new File(CURRENT_DIR + "/current_game.txt").delete();
        new File(LOG_FILE).delete();
    }
    
    private String getDirectory(DifficultyEnum level) {
        switch (level) {
            case EASY: return EASY_DIR;
            case MEDIUM: return MEDIUM_DIR;
            case HARD: return HARD_DIR;
            case INCOMPLETE: return CURRENT_DIR;
            default: throw new IllegalArgumentException("Invalid level");
        }
    }
    
    public void appendLog(UserAction action) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            writer.println(action.toString());
        }
    }
    
    public UserAction getLastLog() throws IOException {
        File file = new File(LOG_FILE);
        if (!file.exists()) return null;
        
        java.util.List<String> lines = new java.util.ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        
        if (lines.isEmpty()) return null;
        return UserAction.fromString(lines.get(lines.size() - 1));
    }
    
    public void removeLastLog() throws IOException {
        File file = new File(LOG_FILE);
        if (!file.exists()) return;
        
        java.util.List<String> lines = new java.util.ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        
        if (!lines.isEmpty()) {
            lines.remove(lines.size() - 1);
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (String line : lines) {
                writer.println(line);
            }
        }
    }
    
}
