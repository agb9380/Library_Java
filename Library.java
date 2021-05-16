package Library2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Library {

	Scanner sc = new Scanner(System.in);

	private String newId;
	private String newPasswd;
	private String newName;
	private String newBirth;
	private String newPhone;

	private String newBno; // 책 번호
	private String newBtitle; // 책 제목
	private String newBAuthor; // 작가
	private String newBGenre; // 장르

	ArrayList<Member> MemberList = new ArrayList<Member>();
	ArrayList<Book> BookList = new ArrayList<Book>();
	ArrayList<Book> MyBookList = new ArrayList<Book>();

	public Library() {
		MemberRead(); // text파일의 회원 정보를 읽어서 MemberList에 저장
		BookRead(); // text파일의 책 정보를 읽어서 BookList에 저장
		MyBookRead();
	}

	public int MenuShow() {

		System.out.println("도서관에 접속하셨습니다. (로그인 1번, 회원가입 2번, 서비스 종료 3번): ");
		int selectNum = sc.nextInt();
		sc.nextLine();
		return selectNum;
	}

	public void SelectMenu() {
		int selectNum = MenuShow();

		switch (selectNum) {
		case 1:
			login();
			break;

		case 2:
			register(); // 회원가입 read-> 아이디 중복 체크-> 아이디 중복있으면 다시 회원가입 정보 입력, 중복없으면 가입 성공
			break;

		case 3:
			System.out.println("서비스를 종료합니다 안녕히가세요.");
			break;

		default:
			System.out.println("잘못입력하셨습니다. 다시 입력해주세요.");
			SelectMenu();
			break;
		}

	}

	public void write() {

		Member m = new Member(newId, newPasswd, newName, newBirth, newPhone);

		try {

			File file = new File("C:\\Users\\HP\\Desktop\\member.txt");
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));

			if (file.isFile() && file.canWrite()) {
				// 쓰기
				bufferedWriter.newLine();
				bufferedWriter.write(
						m.getId() + ":" + m.getPasswd() + ":" + m.getName() + ":" + m.getBirth() + ":" + m.getPhone());
				// 개행문자쓰기
				bufferedWriter.close();
			}

//	         dos.writeBytes(m.getId() + ":" + m.getPasswd() + ":" + m.getName()+ ":" + m.getBirth()+ ":" + m.getPhone() + "/"); // writeBytes 사용하니까 슬래쉬 출력 이상하게 되는 것 고쳐짐

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void MemberRead() { // 회원 명부를 읽는것과 동일함, 메모장 읽어서 BookList에 추가함

		try {

			String[] splitMember;

			File file = new File("C:\\Users\\HP\\Desktop\\member.txt");
			FileReader filereader = new FileReader(file);

			BufferedReader bufReader = new BufferedReader(filereader);
			String line = "";

			MemberList.clear(); // 리스트 초기화, manager()에서 bookRead()해서 수정된 사항 전체에 반영하기 위해

			while ((line = bufReader.readLine()) != null) {
				splitMember = line.split(":");
				MemberList.add(
						new Member(splitMember[0], splitMember[1], splitMember[2], splitMember[3], splitMember[4]));
			}

			bufReader.close();

		}

		catch (FileNotFoundException e) {
		} catch (IOException e) {
			System.out.println(e);
		}

	}

	public void login() {

		while (true) {

			System.out.println("=====로그인 시작=====");
			System.out.println("ID: ");
			newId = sc.nextLine();
			System.out.println("Password: ");
			newPasswd = sc.nextLine();

			boolean check_id = false; // for문과 while문을 벗어나게 해주기 위해 사용하는 값
			boolean exit_check = false;

			for (int i = 0; i < MemberList.size(); i++) {
				if (newId.equals(MemberList.get(i).getId())) {

					check_id = true; // 메모장 내부에 id가 존재한다. 아이디 정답

					if (newPasswd.equals(MemberList.get(i).getPasswd())) {

						System.out.println("로그인 되었습니다. 반갑습니다." + newId + "님");
						if(newId.compareTo("admin") == 0) {
							manager();
						} else {
							user();
						}
						exit_check = true; // 아이디와 비밀번호가 모두 정답인 경우, 반복문을 나가기 위해서 사용

						break; // for문 나감

					}

					else {

						System.out.println("비밀번호를 잘못 입력하셨습니다.");
						break;

					}

				}
			}

			if (exit_check == true)
				break; // while 반복문 나감

			if (check_id == false) {

				System.out.println("입력하신 아이디가 존재하지 않습니다."); // 아이디가 틀린 경우 이므로, 다시 로그인 기회얻음 while 돌아감

			}

		}

	}

	public void manager() {

		BookRead(); // BookRead()를 계속 써주어서 로그인 -> 책 수정 or 책 등록-> 다시 전체 도서 조회 -> 책 전체 리스트가 여러 번 출력됐음
					// => 생성자에 써줌,
					// 다시 주석 풀어줌 delete는 됐는데, 새로운 도서 등록 후 전체 도서 조회 반영이 안됐음, list.claer() 사용해서 리스트
					// 초기화, 여러 번 출력하는거 해결
					// delete가 된 이유는 remove해서 리스트에서 바로 제거됐으니까 따로 파일 읽어오고 할 필요없어서임

		MemberRead();

		System.out.println("==========관리자가 선택할 수 있는 메뉴==========");
		System.out.println("1. 전체 도서 조회 2. 새로운 도서 등록 3. 책 정보 수정 4. 도서 조회 5. 로그아웃 6. 전체 회원 조회 7.회원 정보 수정");
		int num = sc.nextInt();
		sc.nextLine();

		switch (num) {
		case 1: // 1. 전체 도서 조회
			managerAllBook();
			break;

		case 2: // 2. 새로운 도서 등록
			BookRegister(); // 회원가입 read-> 아이디 중복 체크-> 아이디 중복있으면 다시 회원가입 정보 입력, 중복없으면 가입 성공
			break;

		case 3: // 3. 책 정보 수정

			int updateNum;	
			printallBook();
//			for (int i = 0; i < BookList.size(); i++) { // 책에 관한 정보 수정할 때 참고할 수 있도록 전체 목록 출력
//				System.out.println("도서 번호: " + BookList.get(i).getbNO() + "  " + "도서 제목: " + BookList.get(i).getbTile()
//						+ "  " + "저자: " + BookList.get(i).getbAuthor() + "  " + "장르: " + BookList.get(i).getbGenre());

				System.out.println("1. 도서 정보 수정 2. 목록에서 도서 제거");

				updateNum = sc.nextInt();
				sc.nextLine();
				switch (updateNum) {

				case 1:
					updateBook(); // BookList Book객체에 정보 넣기-> 수정된 사항 파일에 write -> update된 파일을 read해서 수정된 내용을 다시
									// 불러오기
					break;
				case 2:
					deletebook();
					break;
				default:
					System.out.println("잘못 입력하셨습니다");
					break;
				}
//			}
			break;
		case 4:
			searchBook(); // 도서를 개별로 조회함
			break;
		case 5:
			System.out.println("로그아웃 되었습니다.");
			SelectMenu();
			break;

		case 6:
			allMember();
			break;

		case 7:
			int updateMemberNum;

			printallMember();
			System.out.println("1. 회원 정보 수정 2. 목록에서 회원 제거");
			updateMemberNum = sc.nextInt();
			sc.nextLine();
			switch (updateMemberNum) {

			case 1:
				updateMember();
				break;
			case 2:
				deleteMember();
				break;
			default:
				System.out.println("잘못 입력하셨습니다");
				break;
			}

			break;

		default:
			System.out.println("잘못 입력하셨습니다.");
			break;
		}

	}
	
	
	public void user() {

		BookRead(); // BookRead()를 계속 써주어서 로그인 -> 책 수정 or 책 등록-> 다시 전체 도서 조회 -> 책 전체 리스트가 여러 번 출력됐음
					// => 생성자에 써줌,
					// 다시 주석 풀어줌 delete는 됐는데, 새로운 도서 등록 후 전체 도서 조회 반영이 안됐음, list.claer() 사용해서 리스트
					// 초기화, 여러 번 출력하는거 해결
					// delete가 된 이유는 remove해서 리스트에서 바로 제거됐으니까 따로 파일 읽어오고 할 필요없어서임

		MemberRead();

		System.out.println("==========일반 유저가 선택할 수 있는 메뉴==========");
		System.out.println("1. 전체 도서 조회 2.도서 대여하기 4. 반납하기 5. 로그아웃 6. 대여 목록 조회 ");
		int num = sc.nextInt();
		sc.nextLine();

		switch (num) {
		case 1: // 1. 전체 도서 조회
			userAllBook();
			break;

		case 2: // 2. 도서 대여하기
			printallBook();
			borrowBook();
			break;
			
			
		case 3: // 3. MyBookList 출력하기, 내가 대여한 책 목록을 보여줘야 함

			int updateNum;
			System.out.println("===전체 도서 목록===");

			for (int i = 0; i < BookList.size(); i++) { // 책에 관한 정보 수정할 때 참고할 수 있도록 전체 목록 출력
				System.out.println("도서 번호: " + BookList.get(i).getbNO() + "  " + "도서 제목: " + BookList.get(i).getbTile()
						+ "  " + "저자: " + BookList.get(i).getbAuthor() + "  " + "장르: " + BookList.get(i).getbGenre());

				System.out.println("1. 도서 정보 수정 2. 목록에서 도서 제거");

				updateNum = sc.nextInt();
				sc.nextLine();
				switch (updateNum) {

				case 1:
					updateBook(); // BookList Book객체에 정보 넣기-> 수정된 사항 파일에 write -> update된 파일을 read해서 수정된 내용을 다시
									// 불러오기
					break;
				case 2:
					deletebook();
					break;
				default:
					System.out.println("잘못 입력하셨습니다");
					break;
				}
			}
			break;
		case 4:   // 반납하기
//			searchBook(); // 도서를 개별로 조회함
			// 내가 가지고 있는 MyBookList를 출력
			
			// 마이 북리스트를 파일에 라이트 해야함
			
			printUserMyBook();
			
//			System.out.println("대여한 책 목록");
//			for (int i = 0; i < MyBookList.size(); i++) { // 책에 관한 정보 수정할 때 참고할 수 있도록 전체 목록 출력
//				System.out.println("도서 번호: " + MyBookList.get(i).getbNO() + "  " + "도서 제목: " + MyBookList.get(i).getbTile()
//						+ "  " + "저자: " + MyBookList.get(i).getbAuthor() + "  " + "장르: " + MyBookList.get(i).getbGenre());
//			}
//			
			returnBook();
			
			
			
			break;
		case 5:
			System.out.println("로그아웃 되었습니다.");
			SelectMenu();
			break;

		case 6:		// 내 목록 조회
			MyBookListRead();

			UserMyBook();
			
			break;
		case 7:
			int updateMemberNum;

			printallMember();
			System.out.println("1. 회원 정보 수정 2. 목록에서 회원 제거");
			updateMemberNum = sc.nextInt();
			sc.nextLine();
			switch (updateMemberNum) {

			case 1:
				updateMember();
				break;
			case 2:
				deleteMember();
				break;
			default:
				System.out.println("잘못 입력하셨습니다");
				break;
			}

			break;

		default:
			System.out.println("잘못 입력하셨습니다.");
			break;
		}

	}
	
	
	public void UserMyBook() {		
		printUserMyBook();
		user();
		
	}
	
	public void printUserMyBook(){
		System.out.println("대여한 책 목록");
		for (int i = 0; i < MyBookList.size(); i++) { // 책에 관한 정보 수정할 때 참고할 수 있도록 전체 목록 출력
			System.out.println("도서 번호: " + MyBookList.get(i).getbNO() + "  " + "도서 제목: " + MyBookList.get(i).getbTile()
					+ "  " + "저자: " + MyBookList.get(i).getbAuthor() + "  " + "장르: " + MyBookList.get(i).getbGenre());
		}
		
		
	}
	

	
	public void borrowBook() {  // 대여하기
		while (true) {
			System.out.println("대여하고 싶은 도서 번호를 입력하세요.");
			String temp = sc.nextLine();
			int cnt = 0;
			for (int i = 0; i < BookList.size(); i++) {
				if (temp.equals(BookList.get(i).getbNO())) {
					
					MyBookList.add(BookList.get(i)); // MyBookList를 추가해주었음, 대여하고 반납하는 List
					
					BookList.remove(BookList.get(i));
//					지웠으니까 파일에 다시 쓰기, remove하고, user() 메소드를 실행시켜서 다시 다읽어버리니까 지워지지 않았음
					updateBookWrite();
					cnt++;
					System.out.println("도서 대여 완료");
					MyBookWrite(); // 업데이트말고, 새로운 파일에 쓰기
					user();
					break;
				}
			}
			if (cnt == 0) {
				System.out.println("해당 도서가 존재하지 않습니다. 도서 번호를 다시 입력하세요.");
			} else {
				break;
			}
		}

	}
	
	public void returnBook() {  // 책 반납하기
		while (true) {
			System.out.println("반납하고 싶은 도서 번호를 입력하세요.");
			String temp = sc.nextLine();
			int cnt = 0;
			for (int i = 0; i < MyBookList.size(); i++) {
				if (temp.equals(MyBookList.get(i).getbNO())) {
					
//					MyBookList.add(BookList.get(i)); // MyBookList를 추가해주었음, 대여하고 반납하는 List
					BookList.add(MyBookList.get(i));
//					updateReturnBookWrite();
					updateBookWrite();
					MyBookList.remove(MyBookList.get(i));
					MyBookWrite(); // 업데이트말고, 새로운 파일에 쓰기
//					지웠으니까 파일에 다시 쓰기, remove하고, user() 메소드를 실행시켜서 다시 다읽어버리니까 지워지지 않았음
					cnt++;
					System.out.println("도서 반납 완료");
					user();
					break;
				}
			}
			if (cnt == 0) {
				System.out.println("해당 도서가 존재하지 않습니다. 도서 번호를 다시 입력하세요.");
			} else {
				break;
			}
		}

	}
	
	
	public void MyBookWrite() {

		BufferedWriter bufferedWriter = null;

		try {

			File file = new File("C:\\Users\\HP\\Desktop\\MyBook.txt");
			bufferedWriter = new BufferedWriter(new FileWriter(file));

			if (file.isFile() && file.canWrite()) {
				// 쓰기
//				bufferedWriter.newLine(); // 
				for (int i = 0; i < MyBookList.size(); i++) {
					bufferedWriter.write(MyBookList.get(i).getbNO() + ":" + MyBookList.get(i).getbTile() + ":"
							+ MyBookList.get(i).getbAuthor() + ":" + MyBookList.get(i).getbGenre());
					bufferedWriter.newLine();
				}
			
				// 개행문자쓰기
				bufferedWriter.flush();
//				bufferedWriter.close();

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedWriter.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}

		}

	}
	
	
	
	
	
	
	public void userAllBook() {
		printallBook();
		
		user();
	}
	


	public void managerAllBook() {
		printallBook();
		manager();
	}

	public void allMember() {
		printallMember();
		manager();
	}

	public void printallMember() {
		System.out.println("===전체 회원 목록===");
		for (int i = 0; i < MemberList.size(); i++) {
			System.out.println("회원 ID: " + MemberList.get(i).getId() + "  " + "비밀번호: " + MemberList.get(i).getPasswd()
					+ "  " + "회원이름: " + MemberList.get(i).getName() + "  " + "생년월일: " + MemberList.get(i).getBirth()
					+ "전화번호: " + MemberList.get(i).getPhone());
		}
	}
	
	public void printallBook() {
		System.out.println("===전체 도서 목록===");
		for (int i = 0; i < BookList.size(); i++) {
			System.out.println("도서 번호: " + BookList.get(i).getbNO() + "  " + "도서 제목: " + BookList.get(i).getbTile()
					+ "  " + "저자: " + BookList.get(i).getbAuthor() + "  " + "장르: " + BookList.get(i).getbGenre());
		}
	}
	

	// 책 삭제
	public void deletebook() {
		while (true) {
			System.out.println("목록에서 제거하고 싶은 책 번호를 입력하세요.");
			String temp = sc.nextLine();
			int cnt = 0;
			for (int i = 0; i < BookList.size(); i++) {
				if (temp.equals(BookList.get(i).getbNO())) {
					BookList.remove(i);
					cnt++;
					System.out.println("도서 삭제 완료");
					updateBookWrite();
					manager();
					break;
				}
			}
			if (cnt == 0) {
				System.out.println("해당 도서가 존재하지 않습니다. 도서 번호를 다시 입력하세요.");
			} else {
				break;
			}
		}

	}

	public void deleteMember() {
		while (true) {
			System.out.println("목록에서 제거하고 싶은 회원 ID를 입력하세요.");
			String temp = sc.nextLine();
			int cnt = 0;
			for (int i = 0; i < MemberList.size(); i++) {
				if (temp.equals(MemberList.get(i).getId())) {
					MemberList.remove(i);
					cnt++;
					System.out.println("회원 삭제 완료");
					updateMemberWrite();
					manager();
					break;
				}
			}
			if (cnt == 0) {
				System.out.println("해당 회원이 존재하지 않습니다. 회원 ID를 다시 입력하세요.");
			} else {
				break;
			}
		}

	}

	// 도서 번호로 도서 검색
	public void searchBook() {
		while (true) {
			System.out.println("검색 할 책의 관리 번호를 입력해주세요");
			String temp = sc.nextLine();
			int cnt = 0;
			for (int i = 0; i < BookList.size(); i++) {
				if (temp.equals(BookList.get(i).getbNO())) {
					System.out.println("책 번호: " + BookList.get(i).getbNO());
					System.out.println("책 제목: " + BookList.get(i).getbTile());
					System.out.println("저자: " + BookList.get(i).getbAuthor());
					System.out.println("장르: " + BookList.get(i).getbGenre());
//                  System.out.println("대여가능 : " + BookList.get(i).isbAvailable());
					cnt++;
					break;
				}
			}
			if (cnt == 0) {
				System.out.println("해당 도서가 존재하지 않습니다. 도서 번호를 다시 입력하세요.");
			} else {
				manager();
				break;
			}
		}
	}

	// 책 정보 수정
	public void updateBook() {
		while (true) {
			System.out.println("수정하고 싶은 책의 번호를 입력하세요.");
			String temp = sc.nextLine();
			int cnt = 0;
			for (int i = 0; i < BookList.size(); i++) {
				if (temp.equals(BookList.get(i).getbNO())) {
					System.out.println("새로운 제목 입력: ");
					BookList.get(i).setbTile(sc.nextLine());
					System.out.println("새로운 저자 입력: ");
					BookList.get(i).setbAuthor(sc.nextLine());
					System.out.println("새로운 장르 입력: ");
					BookList.get(i).setbGenre(sc.nextLine());
					cnt++;
					updateBookWrite();
					System.out.println("도서 수정 완료");
					manager();
					break;
				}
			}
			if (cnt == 0) {
				System.out.println("해당 도서가 존재하지 않습니다. 도서 번호를 다시 입력하세요.");
			} else {
				break;
			}
		}
	}

	// 회원 정보 수정
	public void updateMember() {
		while (true) {
			System.out.println("수정하고 싶은 회원의 ID를 입력하세요.");
			String temp = sc.nextLine();
			int cnt = 0;
			for (int i = 0; i < MemberList.size(); i++) {
				if (temp.equals(MemberList.get(i).getId())) {
					System.out.println("새로운 비밀번호 입력: ");
					MemberList.get(i).setPasswd(sc.nextLine());
					System.out.println("새로운 회원 이름 입력: ");
					MemberList.get(i).setName(sc.nextLine());
					System.out.println("새로운  회원 생년월일: ");
					MemberList.get(i).setBirth(sc.nextLine());
					System.out.println("새로운 전화번호 입력: ");
					MemberList.get(i).setPhone(sc.nextLine());
					cnt++;
					updateMemberWrite();
					System.out.println("회원 수정 완료");
					manager();
					break;
				}
			}
			if (cnt == 0) {
				System.out.println("해당 회원이 존재하지 않습니다. 도서 번호를 다시 입력하세요.");
			} else {
				break;
			}
		}
	}

	public void register() {

		while (true) {
			int cnt = 0;

			System.out.println("=====회원가입 시작=====");
			System.out.println("ID: ");
			newId = sc.nextLine();
			boolean isExists = true; // 처음에 실수

			for (int i = 0; i < MemberList.size(); i++) {
				if (newId.equals(MemberList.get(i).getId())) {
					isExists = false; // 동일한 id가 존재하면 false값을 넣음
					break; // for문 나가기
				}
			}

			if (isExists) { // isExists=true;
				System.out.println("Password: ");
				newPasswd = sc.nextLine();
				System.out.println("name: ");
				newName = sc.nextLine();
				System.out.println("Birth: ");
				newBirth = sc.nextLine();
				System.out.println("Phone: ");
				newPhone = sc.nextLine();

				cnt++;
				write();
				System.out.println("회원가입 되었습니다. 로그인 해주세요.");
				MemberRead(); // 회원가입 후 바로 로그인하기 위해 다시 읽어줌
				login();
				break;

			}
			if (cnt == 0) { // isExists=false; 동일한 아이디가 존재하니까, 중복이 존재함
				System.out.println("중복된 아이디가 존재합니다. 다른 아이디를 입력해주세요. ");
			} else {
				break;
			}
		}

	}

	public void BookRegister() {

		BookRead(); // 책들이 저장되어 있는 txt파일을 읽어야 함, 중복되는거 등록안하려면.., 이때 읽으면서 텍스트 파일에 있는 것들 BookList에
					// 추가해주어야함

		while (true) {
			int cnt = 0;
			System.out.println("=====새로운 책 등록 시작=====");
			System.out.println("책 번호를 입력하세요.");
			newBno = sc.nextLine();
			boolean isExists = true;

			for (int i = 0; i < BookList.size(); i++) {
				if (newBno.equals(BookList.get(i).getbNO())) {
					isExists = false; // id same exists => out
					break;
				}

			}

			if (isExists) {

				System.out.println("도서명 : ");
				newBtitle = sc.nextLine();
				System.out.println("저자: ");
				newBAuthor = sc.nextLine();
				System.out.println("책 장르: ");
				newBGenre = sc.nextLine();
				cnt++;

				BookWrite();
//				updateBookWrite();
//				read();
				System.out.println("새로운 책이 등록되었습니다. 메뉴를 선택해주세요.");
				manager();
				break;

			}
			if (cnt == 0) {
				System.out.println("중복된 책 번호가 존재합니다. 다른 책 번호를 입력해주세요. ");
			} else {
				break;
			}

		}
	}

	public void BookRead() { // 회원 명부를 읽는것과 동일함, 메모장 읽어서 BookList에 추가함

		try {

			String[] splitBook;

			File file = new File("C:\\Users\\HP\\Desktop\\book.txt");
			FileReader filereader = new FileReader(file);

			BufferedReader bufReader = new BufferedReader(filereader);
			String line = "";

			BookList.clear(); // 리스트 초기화, manager()에서 bookRead()해서 수정된 사항 전체에 반영하기 위해

			while ((line = bufReader.readLine()) != null) {
				splitBook = line.split(":");
				BookList.add(new Book(splitBook[0], splitBook[1], splitBook[2], splitBook[3]));
			}

			bufReader.close();

		}

		catch (FileNotFoundException e) {
		} catch (IOException e) {
			System.out.println(e);
		}

	}
	
	
	public void MyBookRead() { // 회원 명부를 읽는것과 동일함, 메모장 읽어서 BookList에 추가함

		try {

			String[] splitBook;

			File file = new File("C:\\Users\\HP\\Desktop\\MyBook.txt");
			FileReader filereader = new FileReader(file);

			BufferedReader bufReader = new BufferedReader(filereader);
			String line = "";

			MyBookList.clear(); // 리스트 초기화, manager()에서 bookRead()해서 수정된 사항 전체에 반영하기 위해

			while ((line = bufReader.readLine()) != null) {
				splitBook = line.split(":");
				MyBookList.add(new Book(splitBook[0], splitBook[1], splitBook[2], splitBook[3]));
			}

			bufReader.close();

		}

		catch (FileNotFoundException e) {
		} catch (IOException e) {
			System.out.println(e);
		}

	}
	
	
	
	public void MyBookListRead() { // 회원 명부를 읽는것과 동일함, 메모장 읽어서 BookList에 추가함

		try {

			String[] splitBook;

			File file = new File("C:\\Users\\HP\\Desktop\\MyBook.txt");
			FileReader filereader = new FileReader(file);

			BufferedReader bufReader = new BufferedReader(filereader);
			String line = "";

			MyBookList.clear(); // 리스트 초기화, manager()에서 bookRead()해서 수정된 사항 전체에 반영하기 위해

			while ((line = bufReader.readLine()) != null) {
				splitBook = line.split(":");
				MyBookList.add(new Book(splitBook[0], splitBook[1], splitBook[2], splitBook[3]));
			}

			bufReader.close();

		}

		catch (FileNotFoundException e) {
		} catch (IOException e) {
			System.out.println(e);
		}

	}
	
	
	

	public void BookWrite() {

		BufferedWriter bufferedWriter = null;
		Book b = new Book(newBno, newBtitle, newBAuthor, newBGenre);

		try {

			File file = new File("C:\\Users\\HP\\Desktop\\book.txt");
			bufferedWriter = new BufferedWriter(new FileWriter(file, true));

			if (file.isFile() && file.canWrite()) {
				// 쓰기
				bufferedWriter.write(b.getbNO() + ":" + b.getbTile() + ":" + b.getbAuthor() + ":" + b.getbGenre());
				// 개행문자쓰기
				bufferedWriter.newLine();
				bufferedWriter.close();
			}

//			for (int i = 0; i < BookList.size(); i++) {
//				bufferedWriter.write(BookList.get(i).getbNO() + ":" + BookList.get(i).getbTile() + ":"
//						+ BookList.get(i).getbAuthor() + ":" + BookList.get(i).getbGenre());
//				bufferedWriter.newLine();
//			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedWriter.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}

		}

	}

	public void updateBookWrite() {

		BufferedWriter bufferedWriter = null;

		try {

			File file = new File("C:\\Users\\HP\\Desktop\\book.txt");
			bufferedWriter = new BufferedWriter(new FileWriter(file));

			if (file.isFile() && file.canWrite()) {
				// 쓰기
//				bufferedWriter.newLine(); // 
				for (int i = 0; i < BookList.size(); i++) {
					bufferedWriter.write(BookList.get(i).getbNO() + ":" + BookList.get(i).getbTile() + ":"
							+ BookList.get(i).getbAuthor() + ":" + BookList.get(i).getbGenre());
					bufferedWriter.newLine();
				}

				// 개행문자쓰기
				bufferedWriter.flush();
//				bufferedWriter.close();

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedWriter.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}

		}

	}

	public void updateMemberWrite() {

		BufferedWriter bufferedWriter = null;

		try {

			File file = new File("C:\\Users\\HP\\Desktop\\member.txt");
			bufferedWriter = new BufferedWriter(new FileWriter(file));

			if (file.isFile() && file.canWrite()) {
				// 쓰기
//				bufferedWriter.newLine(); // 
				for (int i = 0; i < MemberList.size(); i++) {
					bufferedWriter.write(MemberList.get(i).getId() + ":" + MemberList.get(i).getPasswd() + ":"
							+ MemberList.get(i).getName() + ":" + MemberList.get(i).getBirth() + ":"
							+ MemberList.get(i).getPhone());
					bufferedWriter.newLine();
				}

				// 개행문자쓰기
				bufferedWriter.flush();
//				bufferedWriter.close();

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedWriter.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}

		}

	}

}