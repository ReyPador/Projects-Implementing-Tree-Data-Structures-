import java.util.*;

class Member {
    String name;
    String role;
    List<Member> children;

    Member(String name, String role) {
        this.name = name;
        this.role = role;
        this.children = new ArrayList<>();
    }

    void addChild(Member child) {
        children.add(child);
    }
}

public class SchoolHierarchyMaker {
    static Scanner scanner = new Scanner(System.in);
    static Member principal;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Principal");
            System.out.println("2. Add Teacher");
            System.out.println("3. Add Student");
            System.out.println("4. Display Hierarchy");
            System.out.println("5. Search Member");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: addPrincipal(); break;
                case 2: addTeacher(); break;
                case 3: addStudent(); break;
                case 4: displayHierarchy(principal, 0); break;
                case 5: searchMember(); break;
                case 6: return;
                default: System.out.println("Invalid choice! Try again.");
            }
        }
    }

    static void addPrincipal() {
        if (principal == null) {
            System.out.print("Enter the name of the principal: ");
            String name = scanner.nextLine();
            principal = new Member(name, "Principal");
        } else {
            System.out.println("Principal already exists!");
        }
    }

    static void addTeacher() {
        if (principal == null) {
            System.out.println("Please add the principal first.");
            return;
        }
        System.out.print("Enter the name of the principal: ");
        String principalName = scanner.nextLine();
        Member principalMember = findMember(principal, principalName);
        if (principalMember != null && "Principal".equalsIgnoreCase(principalMember.role)) {
            System.out.print("Enter the name of the teacher: ");
            String teacherName = scanner.nextLine();
            principalMember.addChild(new Member(teacherName, "Teacher"));
        } else {
            System.out.println("Principal not found.");
        }
    }

    static void addStudent() {
        if (principal == null) {
            System.out.println("Please add the principal first.");
            return;
        }
        System.out.print("Enter the name of the teacher: ");
        String teacherName = scanner.nextLine();
        Member teacher = findMember(principal, teacherName);
        if (teacher != null && "Teacher".equalsIgnoreCase(teacher.role)) {
            System.out.print("Enter the name of the student: ");
            String studentName = scanner.nextLine();
            teacher.addChild(new Member(studentName, "Student"));
        } else {
            System.out.println("Teacher not found.");
        }
    }

    static Member findMember(Member member, String name) {
        if (member == null) return null;
        if (member.name.equalsIgnoreCase(name)) return member;
        for (Member child : member.children) {
            Member found = findMember(child, name);
            if (found != null) return found;
        }
        return null;
    }

    static void displayHierarchy(Member member, int level) {
        if (member == null) return;
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < level * 4; i++) indent.append(" ");
        System.out.println(indent + "- " + member.name + " (" + member.role + ")");
        for (Member child : member.children) displayHierarchy(child, level + 1);
    }

    static void searchMember() {
        if (principal == null) {
            System.out.println("The hierarchy is empty. Please add the principal first.");
            return;
        }
        System.out.print("Enter the name of the member to search: ");
        String searchName = scanner.nextLine();
        Member member = findMember(principal, searchName);
        if (member != null) {
            System.out.println("Found: " + member.name + " (" + member.role + ")");
            if (!member.children.isEmpty()) {
                System.out.println("Children of " + member.name + ":");
                for (Member child : member.children) {
                    System.out.println("- " + child.name + " (" + child.role + ")");
                }
            } else {
                System.out.println(member.name + " has no children.");
            }
        } else {
            System.out.println("Member not found.");
        }
    }
}
