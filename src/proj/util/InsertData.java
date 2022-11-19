package proj.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import proj.dao.BookDAO;
import proj.vo.BookVO;

public class InsertData {
	BookDAO bDao = new BookDAO();
	static ArrayList<BookVO> list = new ArrayList<>();
	
	String title = null;
	String auth = null;
	String pub = null;
	
    public static void main(String[] args) {
    	InsertData csvReader = new InsertData();
        csvReader.readCSV();
        csvReader.regAllBook();
    }

    public void readCSV() {
        File csv = new File("src\\proj\\resources\\data.csv");
        BufferedReader br = null;
        String line = "";

        try {
            br = new BufferedReader(new FileReader(csv));
            while ((line = br.readLine()) != null) { // readLine()은 파일에서 개행된 한 줄의 데이터를 읽어온다.
            	BookVO bvo = new BookVO();
                String[] lineArr = line.split(","); // 파일의 한 줄을 ,로 나누어 배열에 저장 후 리스트로 변환한다.
                System.out.println(Arrays.toString(lineArr));
                bvo.setBookName(lineArr[0]);
    			bvo.setAuthor(lineArr[1]);
    			bvo.setPublisher(lineArr[2]);
                list.add(bvo);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) { 
                    br.close(); // 사용 후 BufferedReader를 닫아준다.
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void regAllBook() {
    	list.remove(0);
    	list.forEach(t -> regBook(t));
    }
    public void regBook(BookVO bvo) {
		if(bDao.insert(bvo)) {
			System.out.println(">> 등록되었습니다.");
		} else {
			System.out.println(">> 등록에 실패하였습니다.");
		}
	}
}
