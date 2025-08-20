package br.com.challanges.algorithms.datastructure.tree;

import java.time.LocalTime;
import java.util.*;

public class MeetingRoomScheduler {

    // Represents a booking interval
    static class Booking {
        LocalTime start;
        LocalTime end;
        String title;

        Booking(LocalTime start, LocalTime end, String title) {
            this.start = start;
            this.end = end;
            this.title = title;
        }

        @Override
        public String toString() {
            return "[" + start + " - " + end + "] " + title;
        }
    }

    // Node for Random Binary Search Tree (Interval Tree)
    static class Node {
        Booking booking;
        Node left, right;
        LocalTime maxEnd; // Max end time in this subtree
        int size;         // Size of subtree
        Random rand = new Random();

        Node(Booking booking) {
            this.booking = booking;
            this.maxEnd = booking.end;
            this.size = 1;
        }

        void update() {
            size = 1 + size(left) + size(right);
            maxEnd = booking.end;
            if (left != null && left.maxEnd.isAfter(maxEnd)) maxEnd = left.maxEnd;
            if (right != null && right.maxEnd.isAfter(maxEnd)) maxEnd = right.maxEnd;
        }

        static int size(Node n) {
            return (n == null) ? 0 : n.size;
        }
    }

    // RBST operations for intervals
    static class IntervalRBST {
        private Node root;
        private Random rand = new Random();

        public void insert(Booking booking) {
            root = insert(root, booking);
        }

        public void delete(Booking booking) {
            root = delete(root, booking);
        }

        public List<Booking> findOverlaps(LocalTime start, LocalTime end) {
            List<Booking> result = new ArrayList<>();
            findOverlaps(root, start, end, result);
            return result;
        }

        // --- Internal RBST logic ---

        public IntervalRBST() {
        }

        private Node insert(Node n, Booking booking) {
            if (n == null) return new Node(booking);
            if (rand.nextInt(n.size + 1) == 0) {
                return insertAtRoot(n, booking);
            }
            if (booking.start.isBefore(n.booking.start)) {
                n.left = insert(n.left, booking);
            } else {
                n.right = insert(n.right, booking);
            }
            n.update();
            return n;
        }

        private Node insertAtRoot(Node n, Booking booking) {
            if (n == null) return new Node(booking);
            if (booking.start.isBefore(n.booking.start)) {
                n.left = insertAtRoot(n.left, booking);
                n = rotateRight(n);
            } else {
                n.right = insertAtRoot(n.right, booking);
                n = rotateLeft(n);
            }
            n.update();
            return n;
        }

        private Node delete(Node n, Booking booking) {
            if (n == null) return null;
            if (booking.start.equals(n.booking.start) &&
                booking.end.equals(n.booking.end) &&
                booking.title.equals(n.booking.title)) {
                return join(n.left, n.right);
            }
            if (booking.start.isBefore(n.booking.start)) {
                n.left = delete(n.left, booking);
            } else {
                n.right = delete(n.right, booking);
            }
            n.update();
            return n;
        }

        private Node join(Node a, Node b) {
            if (a == null) return b;
            if (b == null) return a;
            if (rand.nextInt(a.size + b.size) < a.size) {
                a.right = join(a.right, b);
                a.update();
                return a;
            } else {
                b.left = join(a, b.left);
                b.update();
                return b;
            }
        }

        private void findOverlaps(Node n, LocalTime start, LocalTime end, List<Booking> result) {
            if (n == null) return;
            if (n.booking.start.isBefore(end) && start.isBefore(n.booking.end)) {
                result.add(n.booking);
            }
            if (n.left != null && n.left.maxEnd.isAfter(start)) {
                findOverlaps(n.left, start, end, result);
            }
            findOverlaps(n.right, start, end, result);
        }

        private Node rotateRight(Node n) {
            Node x = n.left;
            n.left = x.right;
            x.right = n;
            n.update();
            x.update();
            return x;
        }

        private Node rotateLeft(Node n) {
            Node x = n.right;
            n.right = x.left;
            x.left = n;
            n.update();
            x.update();
            return x;
        }
    }

    // Scheduler with one RBST per room
    private Map<String, IntervalRBST> rooms = new HashMap<>();

    public void addBooking(String roomId, LocalTime start, LocalTime end, String title) {
        rooms.putIfAbsent(roomId, new IntervalRBST());
        rooms.get(roomId).insert(new Booking(start, end, title));
    }

    public void removeBooking(String roomId, LocalTime start, LocalTime end, String title) {
        IntervalRBST tree = rooms.get(roomId);
        if (tree != null) {
            tree.delete(new Booking(start, end, title));
        }
    }

    public List<Booking> findConflicts(String roomId, LocalTime start, LocalTime end) {
        IntervalRBST tree = rooms.get(roomId);
        return (tree != null) ? tree.findOverlaps(start, end) : Collections.emptyList();
    }

    // --- Example Usage ---
    public static void main(String[] args) {
        MeetingRoomScheduler scheduler = new MeetingRoomScheduler();

        scheduler.addBooking("RoomA", LocalTime.of(9, 0), LocalTime.of(10, 30), "Marketing Meeting");
        scheduler.addBooking("RoomA", LocalTime.of(9, 0), LocalTime.of(11, 30), "Marketing Meeting");
        scheduler.addBooking("RoomA", LocalTime.of(10, 0), LocalTime.of(11, 0), "Development Sync");
        scheduler.addBooking("RoomA", LocalTime.of(13, 0), LocalTime.of(14, 30), "Client Call");

        List<Booking> conflicts = scheduler.findConflicts("RoomA", LocalTime.of(10, 15), LocalTime.of(10, 45));

        System.out.println("Conflicts with [10:15 - 10:45]:");
        for (Booking b : conflicts) {
            System.out.println("  " + b);
        }
    }
}
