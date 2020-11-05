package top.qiuk.algorithm;


public class 动物歌 {

    public static void main(String[] args) {

//        String song =
//                "There was an old lady who swallowed a fly.\n" +
//                        "I don't know why she swallowed a fly - perhaps she'll die!\n" +
//                        "\n" +
//                        "There was an old lady who swallowed a spider;\n" +
//                        "That wriggled and wiggled and tickled inside her.\n" +
//                        "She swallowed the spider to catch the fly;\n" +
//                        "I don't know why she swallowed a fly - perhaps she'll die!\n" +
//                        "\n" +
//                        "There was an old lady who swallowed a bird;\n" +
//                        "How absurd to swallow a bird.\n" +
//                        "She swallowed the bird to catch the spider,\n" +
//                        "She swallowed the spider to catch the fly;\n" +
//                        "I don't know why she swallowed a fly - perhaps she'll die!\n" +
//                        "\n" +
//                        "There was an old lady who swallowed a cat;\n" +
//                        "Fancy that to swallow a cat!\n" +
//                        "She swallowed the cat to catch the bird,\n" +
//                        "She swallowed the bird to catch the spider,\n" +
//                        "She swallowed the spider to catch the fly;\n" +
//                        "I don't know why she swallowed a fly - perhaps she'll die!\n" +
//                        "\n" +
//                        "There was an old lady who swallowed a dog;\n" +
//                        "What a hog, to swallow a dog!\n" +
//                        "She swallowed the dog to catch the cat,\n" +
//                        "She swallowed the cat to catch the bird,\n" +
//                        "She swallowed the bird to catch the spider,\n" +
//                        "She swallowed the spider to catch the fly;\n" +
//                        "I don't know why she swallowed a fly - perhaps she'll die!\n" +
//                        "\n" +
//                        "There was an old lady who swallowed a cow;\n" +
//                        "I don't know how she swallowed a cow!\n" +
//                        "She swallowed the cow to catch the dog,\n" +
//                        "She swallowed the dog to catch the cat,\n" +
//                        "She swallowed the cat to catch the bird,\n" +
//                        "She swallowed the bird to catch the spider,\n" +
//                        "She swallowed the spider to catch the fly;\n" +
//                        "I don't know why she swallowed a fly - perhaps she'll die!\n" +
//                        "\n" +
//                        "There was an old lady who swallowed a horse...\n" +
//                        "...She's dead, of course!";
//
//        System.out.println(song);

        String[] strings = new String[]{"fly", "spider", "bird", "cat", "dog", "cow", "horse"};

        String song = new 动物歌().song(strings, 2);
        System.out.println(song);
    }

    public String song(String[] strings, int num) {
        String row1 = "There was an old lady who swallowed a ";
        String row2 = "That wriggled and wiggled and tickled inside her.\n";

        String row3_1 = "She swallowed the ";
        String row3_2 = " to catch the ";
        String row3_3 = ".\n";

        String rowEnd = "I don't know why she swallowed a fly - perhaps she'll die!\n";

        StringBuilder sb = new StringBuilder();


        if (num == 0) {
            return "";
        }

        if (num == 1) {
            sb.append(row1).append(strings[0]).append(row3_3);
            return sb.toString();
        }

        sb.append(row1).append(strings[0]).append(row3_3).append("\n");
        for (int i = 0; i < num - 2; i++) {

            sb.append(row1).append(strings[i + 1]).append("\n");
            sb.append(row2);
            for (int j = i + 1; j > 0; j--) {
                sb.append(row3_1).append(strings[j]).append(row3_2).append(strings[j - 1]).append(row3_3);
            }

            sb.append(rowEnd);
            sb.append("\n");
        }
        sb.append("...She's dead, of course!");
        return sb.toString();
    }
}
