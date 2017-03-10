package level;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

import escape.Game;
import util.util;

public class Level {

	public Game game;
	public int[] bombs;
	// bombs[] --> 0 = safe; 1 = bomb; 2 = clicked; 3 = losing bomb;

	public int[] numbers;
	//numbers[] returns number of bombs around

	public int[] flags;
	//flags[] --> 0 = no flag; 1 = flag
	
	public Level(Game game, int initial_index){
		this.game = game;
		bombs = new int[game.height*game.width];
		numbers = new int[game.height*game.width];
		flags = new int[game.height*game.width];

		for(int i=0;i<bombs.length;i++)
			bombs[i]=0;
		
		generateBombs(initial_index);
		generateNumbers();
	}
	

	public void generateBombs(int initial_index){
		//DO STUFF TO CREATE BOMB LOCATIONS
		
		ArrayList<Integer> spaces =new ArrayList<Integer>();
		for(int i=0; i<game.height*game.width;i++){
			spaces.add(i);
		}
		
		ArrayList<Integer>safe_indexes = safeIndexes(initial_index);
		for (int i =0;i<safe_indexes.size();i++){https://c7.staticflickr.com/9/8160/7214525854_733237dd83_z.jpg
			spaces.remove(safe_indexes.get(i));
		}
		
		for (int i=0;i<game.bomb_count;i++){
			int rand = (int) (Math.random()*spaces.size());
			bombs[spaces.get(rand)]=1;
			spaces.remove(rand);
		}
		
		/*for (int i =0;i<game.bomb_count;i++){
			int random = (int) (Math.random()*(game.height*game.width));
			bombs[random]=1;
		}*/
	}
	
	public void generateNumbers(){
		for (int i=0; i<numbers.length;i++){
			int count =0;
			count+=getBomb(i, -1,-1);
			count+=getBomb(i, 0,-1);
			count+=getBomb(i, 1,-1);
			count+=getBomb(i, -1,0);
			count+=getBomb(i, 1,-0);
			count+=getBomb(i, -1,1);
			count+=getBomb(i, 0,1);
			count+=getBomb(i, 1,1);
			numbers[i]=count;
		}
			
	}
	
	public int getBomb(int slot,int dx,int dy){
		int x = util.toPoint(slot, game.width).x;
		int y = util.toPoint(slot, game.width).y;
		
		if (x+dx>=game.width || x+dx<0 || y+dy>=game.height||y+dy<0)
			return 0;
		if (bombs[util.toIndex(x+dx, y+dy, game.width)]==1)
			return 1;
		else return 0;
	}
	
	public ArrayList<Integer> safeIndexes(int initial_index){
		ArrayList<Integer> return_ints = new ArrayList<>();
		return_ints.add(util.safeOffsetIndex(initial_index, -1, -1, game.width, game.height));
		return_ints.add(util.safeOffsetIndex(initial_index, 0, -1, game.width, game.height));
		return_ints.add(util.safeOffsetIndex(initial_index, 1, -1, game.width, game.height));
		return_ints.add(util.safeOffsetIndex(initial_index, -1, 0, game.width, game.height));
		return_ints.add(util.safeOffsetIndex(initial_index, 0, 0, game.width, game.height));
		return_ints.add(util.safeOffsetIndex(initial_index, 1, 0, game.width, game.height));
		return_ints.add(util.safeOffsetIndex(initial_index, -1, 1, game.width, game.height));
		return_ints.add(util.safeOffsetIndex(initial_index, 0, 1, game.width, game.height));
		return_ints.add(util.safeOffsetIndex(initial_index, 1, 1, game.width, game.height));
		
		return return_ints;
	}
	
	public void saveLevel(){
		String filepath = "savelevel.txt";
		/*try (DataOutputStream out = new DataOutputStream 
				(new BufferedOutputStream(new FileOutputStream(filepath)))){
			out.writeInt(5);
			out.writeInt(7);
		}
		catch(IOException e){
			e.printStackTrace();
		}*/
		try ( BufferedWriter out = new BufferedWriter(new FileWriter(filepath))){
			out.write  (Integer.toString(game.width));
			out.write(game.height);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadLevel() {
		String filepath = "savelevel.txt";
		try (Scanner in = new Scanner(new BufferedReader(new FileReader(filepath)))){
			while (in.hasNext())
			{
				if (in.hasNextInt())
					System.out.println(in.nextInt());
				else
					System.out.println("Error char: " + in.next());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
