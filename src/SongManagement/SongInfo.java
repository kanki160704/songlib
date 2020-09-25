package SongManagement;

/**
 *
 * @author : Chenqi Zhao, Ruiqi Zhang
 * @date : ${2020.9.24}
 */

import java.io.*;
import java.util.ArrayList;

public class SongInfo {
    public ArrayList<String[]> songList;
    public SongInfo() {
        ArrayList<String[]> songList= new ArrayList<String[]>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("src/data/SongList.txt"));
            String text = null;
            int i = 0;
            while ((text = br.readLine()) != null) {
                String[] tempLine = text.split("\\|");
                songList.add(tempLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            this.songList = songList;
        }
    }

    private static String[] addStringList(String[] strList) {
        String[] result = new String[4];
        for (int i = 0; i < strList.length; i ++) {
            result[i] = strList[i];
        }
        for (int i = strList.length; i < result.length; i ++) {
            result[i] = "";
        }
        return result;
    }

    public SongInfo(ArrayList<String[]> songList) {
        this.songList = songList;
    }

    public void writeBack() {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter("src/data/SongList.txt"));
            for (int i = 0; i < this.songList.size(); i ++) {
                String[] tempSong = this.songList.get(i);
                String str = tempSong[0] + "|" + tempSong[1] + "|" + tempSong[2] + "|" + tempSong[3];
                bw.write(str);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.flush();
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer("");
        int n = this.songList.size();
        for (int i = 0; i < n; i ++) {
            String[] song = this.songList.get(i);
            for (int j = 0; j < song.length; j ++) {
                result.append(song[j] + " ");
            }
            result.append("\n");
        }
        return String.valueOf(result);
    }
}
