package com;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bh183.nugrahawp.InputActivity;
import com.bh183.nugrahawp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_film";
    private final static String TABLE_FILM ="t_film";
    private final static String KEY_ID_FILM ="Id_Film";
    private final static String KEY_JUDUL ="Judul";
    private final static String KEY_TGL ="Tanggal";
    private final static String KEY_GAMBAR ="Gambar";
    private final static String KEY_CAPTION ="Caption";
    private final static String KEY_PENULIS ="Penulis";
    private final static String KEY_ISI_FILM ="Isi_Film";
    private final static String KEY_LINK ="Link";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault());
    private Context context;


    public DatabaseHandler(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_FILM = "CREATE TABLE " + TABLE_FILM
                + "(" + KEY_ID_FILM + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUL + " TEXT, " + KEY_TGL + " DATE, "
                + KEY_GAMBAR + " TEXT, " + KEY_CAPTION + " TEXT, "
                + KEY_PENULIS + " TEXT, " + KEY_ISI_FILM + " TEXT, "
                + KEY_LINK + " TEXT);";

        db.execSQL(CREATE_TABLE_FILM);
        inisialisasiFilmAwal(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_FILM;
        db.execSQL(DROP_TABLE);
        onCreate(db);

    }

    public void tambahFilm(Film dataFilm) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_CAPTION, dataFilm.getCaption());
        cv.put(KEY_PENULIS, dataFilm.getPenulis());
        cv.put(KEY_LINK, dataFilm.getLink());
        db.insert(TABLE_FILM, null, cv);
        db.close();
    }

    public void tambahFilm(Film dataFilm, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_CAPTION, dataFilm.getCaption());
        cv.put(KEY_PENULIS, dataFilm.getPenulis());
        cv.put(KEY_LINK, dataFilm.getLink());
        db.insert(TABLE_FILM, null, cv);
    }

    public void editFilm(Film dataFilm) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_CAPTION, dataFilm.getCaption());
        cv.put(KEY_PENULIS, dataFilm.getPenulis());
        cv.put(KEY_LINK, dataFilm.getLink());

        db.update(TABLE_FILM, cv,KEY_ID_FILM + "=?", new String[]{String.valueOf(dataFilm.getIdFilm())});
        db.close();
    }

    public void hapusFilm(int idFilm) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_FILM, KEY_ID_FILM + "=?", new String[]{String.valueOf(idFilm)});
        db.close();
    }

    public ArrayList<Film> getAllFilm() {
        ArrayList<Film> dataFilm = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_FILM;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if(csr.moveToFirst()){
            do {
                Date tempDate = new Date();
                try{
                    tempDate = sdFormat.parse(csr.getString(2));
                } catch (ParseException er) {
                    er.printStackTrace();
                }

                Film tempFilm = new Film(
                        csr.getInt(0),
                        csr.getString(1),
                        tempDate,
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6),
                        csr.getString(7)

                );

                dataFilm.add(tempFilm);
            } while (csr.moveToNext());
        }

        return dataFilm;
    }

    private String storeImageFile(int id) {
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
}

    private  void inisialisasiFilmAwal(SQLiteDatabase db) {
        int idFilm = 0;
        Date tempDate = new Date();

        // Menambah data film ke-1
        try {
            tempDate = sdFormat.parse("13/03/2020 06:22");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Film film1 = new Film(
                idFilm,
                "Money Heist",
                tempDate,
                storeImageFile(R.drawable.film1),
                "Rating 8.1",
                "Mutia Fauzia",
                "Money Heist yang tayang pertama kali pada 2017 bercerita tentang aksi 'mastermind' yang disebut 'The Professor' (Alvaro Morte) dalam melakukan perampokan terbesar dalam sejarah Spanyol.\n" +
                        "\n" +
                        "Dalam dua musim penayangan pertamanya, serial ini bercerita sang profesor yang merekrut delapan orang yang disamarkan dengan nama-nama kota di dunia yaitu Tokyo, Moskow, Berlin, Nairobi, Rio, Denver, Helsinki, dan Oslo.\n" +
                        "\n" +
                        "Lewat perencanaan selama lima bulan, kedelapan orang tersebut masuk dan merampok gedung \"Royal Mint of Spain\" di Madrid yang merupakan tempat percetakan uang negara. Kostum yang dipakai kedelapannya dalam merampok sama dengan yang dipakai Neymar dan Mbappe. 'Jumpsuit' warna merah dengan topeng yang menyerukai pelukis Surealis Spanyol, Salvador Dali, yang terkenal dengan bukan hanya karena lukisannya, tetapi kumisnya yang menjulang tipis.\n" +
                        "\n" +
                        "Dalam dua musim pertama tersebut, kedelapannya, dengan dibantu Profesor yang mengendalikan dari sebuah 'safe house' mencetak uang Euro yang bernilai 2,4 Triliun dalam waktu beberapa minggu saja. Tentunya dengan banyak drama yang terjadi di dalam dan di luar gedung, mereka berhasil membawa uang dan berpencar ke beberapa tempat di dunia.\n",
                "https://money.kompas.com/read/2020/03/13/062200526/terhantam-corona-3-wilayah-ini-bakal-duluan-uji-coba-kartu-pra-kerja"

            );

        tambahFilm(film1, db);
        idFilm++;

        // Data film ke-2
        try {
            tempDate = sdFormat.parse("13/03/2020 06:15");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Film film2 = new Film(
                idFilm,
                "Pengabdi Setan",
                tempDate,
                storeImageFile(R.drawable.film2),
                "Rating 7.0",
                "David Oliver Purba",
                " Pengabdi Setan berkisah tentang satu keluarga tahun 1980-an yang terdiri atas ibu, bapak, empat anak dan seorang nenek. Mereka tinggal di rumah terpencil di tengah rimbunnya pepohonan.\n" +
                        "Sang ibu yang merupakan penyanyi, sudah tiga tahun mengidap penyakit aneh dan dia akhirnya meninggal. Sehari setelahnya, bapak terpaksa pergi ke kota untuk suatu urusan, meninggalkan empat anaknya dan si nenek.\n" +
                        "Berbagai kejadian ganjil mulai terjadi di rumah keluarga itu. Rupanya ibu kembali dan menebar teror kepada keluarganya yang masih hidup. Semua terjadi karena semasa hidupnya, ibu mengalami fase putus asa karena tak kunjung punya keturunan. Dia lantas menyembah setan guna memenuhi keinginannya memiliki anak.\n" +
                        "Motif adalah istilah generik yang meliputi semua faktor internal yang mengarah pada sejumlah perilaku untuk mencapai keinginan. Sementara motivasi merupakan istilah yang lebih umum yang merujuk kepada seluruh proses gerakan, termasuk situasi yang menjadi dorongan dari dalam diri individu, tingkah laku yang ditimbulkannya, dan keinginan akhir.\n" +
                        "Memiliki anak tergolong pemenuhan kebutuhan cinta. Dalam hierarki pemenuhan kebutuhan, cinta dan rasa memiliki-dimiliki berada di tingkat ketiga setelah kebutuhan fisiologis dan kebutuhan rasa aman.\n" +
                        "Jadi, jelas bahwa motif dan motivasi ibu untuk menjadi budak setan adalah karena keputusasaan dan cinta. Motif dan motivasi ibu itu dijelaskan sedikit demi sedikit sepanjang film dan sukses menjadi kejutan.\n" +
                        "\n",
                "https://regional.kompas.com/read/2020/03/13/06150071/prajurit-tni-penjual-senjata-dan-amunisi-ke-kkb-divonis-penjara-seumur-hidup"

        );
        tambahFilm(film2, db);
        idFilm++;

        // Data film ke-3
        try {
            tempDate = sdFormat.parse("12/03/2020 22:46");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Film film3 = new Film(
                idFilm,
                "Game of Thrones",
                tempDate,
                storeImageFile(R.drawable.film3),
                "Rating 8.9",
                "Ardi Priyatno Utomo",
                "Sesuai dengan judulnya, Game of Thrones (GoT) menceritakan perjalanan para karakter dalam mencapai takhta. Jika para pembaca tulisan ini merupakan penonton setia serial GoT, tentunya sudah tahu bagaimana perjalanan dari setiap karakter dalam mencapai takhta tersebut. Intrik, politik, manipulasi bahkan pertumpahan darah adalah proses yang mewarnai perjalanan para karakter dalam mencapai takhta (Iron Thrones). Meskipun film yang diadaptasi dari novel George R.R. Martin ini adalah kisah fiktif, namun bagi penulis, kisah perjalanan GoT sangat realistis dan sarat akan makna untuk dijadikan bahan pembelajaran/renungan bersama.",
                "https://www.kompas.com/global/read/2020/03/12/224607170/virus-corona-duterte-umumkan-rencana-lockdown-ibu-kota-filipina"

        );
        tambahFilm(film3, db);
        idFilm++;

        // Data film ke-4
        try {
            tempDate = sdFormat.parse("13/03/2020 05:58");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Film film4 = new Film(
                idFilm,
                "Suicide Squad",
                tempDate,
                storeImageFile(R.drawable.film4),
                "Rating 8.8",
                "Rakhmat Nur Hakim",
                "Suicide Squad adalah sebuah film pahlawan super Amerika Serikat produksi tahun 2016 yang diangkat dari antihero DC Comics dengan judul yang sama. Film tersebut dijadikan installment ketiga dalam DC Extended Universe. Film tersebut ditulis dan disutradarai oleh David Ayer dan dibintangi oleh Will Smith, Jared Leto, Margot Robbie, Joel Kinnaman, Viola Davis, Jai Courtney, Jay Hernandez, Adewale Akinnuoye-Agbaje, Ike Barinholtz, Scott Eastwood, dan Cara Delevingne.\n" +
                        "\n" +
                        "Pada Februari 2009, film Suicide Squad dikembangkan oleh Warner Bros. Pictures. Ayer sepakat untuk menulis dan menyutradarainya pada September 2014, dan pada bulan Oktober proses pemeranannya dimulai. Pengambilan gambar utama dimulai pada 13 April 2015 di Toronto, Ontario, Kanada dengan pemfilman tambahan di Chicago, Illinois, dan berakhir pada bulan Agustus pada tahun tersebut. Film tersebut dirilis pada tanggal 5 Agustus 2016.[4][5][6]\n" +
                        "\n" +
                        "Suicide Squad ditayangkan perdana di New York City pada tanggal 1 Agustus 2016, dan dirilis di Amerika Serikat pada tanggal 5 Agustus 2016, dalam 2D, 3D dan IMAX 3D. Setelah debut yang kuat yang menghasilkan rekor box office baru, film ini meraup lebih dari $745 juta di seluruh dunia, menjadikannya film terlaris yang meraih kesepuluh tertinggi tahun 2016. Ini mendapat ulasan negatif dari kritikus, yang mengkritik plot, arahan dan karakter, meskipun kinerja Robbie mendapat pujian. Film ini dinominasikan untuk dan memenangkan beberapa penghargaan di berbagai kategori, termasuk rambut dan make-up, akting, dan musik.\n",
                "https://nasional.kompas.com/read/2020/03/13/05584191/3-pasien-sembuh-dari-covid-19-harapan-indonesia-di-tengah-pandemi-global"

        );

        tambahFilm(film4, db);
    }
}