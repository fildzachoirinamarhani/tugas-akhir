<?php
function normal()
{
    global $koneksi;
    $aksi = isset($_GET['aksi']) ? $_GET['aksi'] : '';
    if ($aksi == 'delete') {
        $sql1 = "SELECT * FROM penginapan WHERE idpenginapan = '" . $_GET['id'] . "'";
        $query1 = mysqli_query($koneksi, $sql1);
        $result1 = mysqli_fetch_array($query1);
        @unlink($result1['foto']);

        $sql1 = "DELETE FROM penginapan WHERE idpenginapan = '" . $_GET['id'] . "'";
        mysqli_query($koneksi, $sql1);
    }
    $sql1 = "SELECT * FROM penginapan ";
    $query1 = mysqli_query($koneksi, $sql1);
?>
    <div style="text-align: right;">
        <a href="admin_penginapan.php?op=tambah">
            <button class="btn btn-primary">Tambah Penginapan</button>
        </a>
        <br /><br />
    </div>
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>No</th>
                <th>Nama Penginapan</th>
                <th>Latitud</th>
                <th>Longitud</th>
                <th>Aksi</th>
            </tr>
        </thead>
        <tbody>
            <?php
            $no = 1;
            while ($result1 = mysqli_fetch_array($query1)) {
            ?>
                <tr>
                    <td><?= $no++ ?></td>
                    <td><?= $result1['namausaha'] ?></td>
                    <td><?= $result1['latitud'] ?></td>
                    <td><?= $result1['longitud'] ?></td>
                    <td><a href="admin_penginapan.php?op=tambah&id=<?= $result1['idpenginapan'] ?>">Edit</a> | <a href="#" <input onclick="konfirmasi('admin_penginapan.php?aksi=delete&id=<?= $result1['idpenginapan'] ?>')" />>Delete</a></td>
                </tr>
            <?php
            }
            ?>
        </tbody>
    </table>
<?php
}
function tambah()
{
    global $koneksi, $idlogin;
    $namausaha = "";
    $keterangan = "";
    $latitud = "";
    $longitud = "";
    $err = "";
    $success = "";
    $foto_nama_db = '';
    $status_edit = false;
    $status_update_foto = true;
?>
    <div>
        <a href="admin_penginapan.php">&lsaquo; Kembali ke halaman admin penginapan</a>
    </div>
    <?php

    $simpan = isset($_POST['simpan']) ? $_POST['simpan'] : '';
    if (isset($_GET['id']) != '') {
        $id = $_GET['id'];
        $sql1 = "SELECT * FROM penginapan WHERE idpenginapan ='$id'";
        $query1 = mysqli_query($koneksi, $sql1);
        $num1 = mysqli_num_rows($query1);
        if ($num1 < 1) {
            $err = "<li>Data tidak ditemukan</li>";
        } else {
            $status_edit = true;
        }
        $result1 = mysqli_fetch_array($query1);
        $namausaha = $result1['namausaha'];
        $foto_nama_db = $result1['foto'];
        $keterangan = $result1['keterangan'];
        $latitud = $result1['latitud'];
        $longitud = $result1['longitud'];
    }

    if ($simpan) {
        $namausaha = bersihkan($_POST['namausaha']);

        $foto_nama = $_FILES['foto']['name'];
        $foto_type = $_FILES['foto']['type'];
        $foto_file = $_FILES['foto']['tmp_name'];

        if (!function_exists('base_url')) {
            function base_url($atRoot = FALSE, $atCore = FALSE, $parse = FALSE)
            {
                if (isset($_FILES['HTTP_HOST'])) {
                    $http = isset($_FILES['HTTPS']) && strtolower($_FILES['HTTPS']) !== 'off' ? 'https' : 'http';
                    $hostname = $_FILES['HTTP_HOST'];
                    $dir =  str_replace(basename($_FILES['SCRIPT_NAME']), '', $_FILES['SCRIPT_NAME']);

                    $core = preg_split('@/@', str_replace($_FILES['DOCUMENT_ROOT'], '', realpath(dirname(__FILE__))), 0, PREG_SPLIT_NO_EMPTY);
                    $core = $core[0];

                    $tmplt = $atRoot ? ($atCore ? "%s://%s/%s/" : "%s://%s/") : ($atCore ? "%s://%s/%s/" : "%s://%s%s");
                    $end = $atRoot ? ($atCore ? $core : $hostname) : ($atCore ? $core : $dir);
                    $base_url = sprintf($tmplt, $http, $hostname, $end);
                } else $base_url = 'https://192.168.43.144/dinporabudpar/imgpenginapan/';

                if ($parse) {
                    $base_url = parse_url($base_url);
                    if (isset($base_url['path'])) if ($base_url['path'] == '/') $base_url['path'] = '';
                }

                return $base_url;
            }
        }

        $upload_folder = "imgpenginapan/";

        move_uploaded_file($foto_file, $upload_folder . $foto_nama);

        $keterangan = bersihkan($_POST['keterangan']);
        $latitud = bersihkan($_POST['latitud']);
        $longitud = bersihkan($_POST['longitud']);

        if ($namausaha == '') {
            $err = $err . "<li>Nama penginapan wajib diisi.</li>";
        }
        if (is_gambar($foto_type) == false && $foto_nama != '') {
            $err = $err . "<li>Foto yang dimasukkan tidak diperbolehkan</li>";
        }
        if ($err == '') {
            if ($status_edit == true) {
                if ($foto_nama == '') {
                    $foto_nama = $foto_nama_db;
                    $status_update_foto = false;
                }
                $sql1 = "UPDATE penginapan SET namausaha = '$namausaha',foto = '$foto_nama',keterangan = '$keterangan',latitud = '$latitud',longitud = '$longitud',idlogin = '$idlogin' WHERE idpenginapan = '$id'";
            } else {
                $sql1 = "INSERT INTO penginapan(namausaha,foto,keterangan,latitud,longitud,idlogin) VALUES ('$namausaha','$foto_nama','$keterangan','$latitud','$longitud','$idlogin')";
            }
            $query1 = mysqli_query($koneksi, $sql1);
            if ($query1) {
                if ($foto_nama) {
                    if ($status_update_foto == true) {
                        @unlink($foto_nama_db);
                        $foto_nama_db = $foto_nama;
                    }
                    move_uploaded_file($foto_file, $foto_nama);
                }
                $success = "Data berhasil disimpan. <a href='admin_penginapan.php'>Kembali ke halaman admin penginapan</a>";
            }
        }
    }
    if ($err) {
    ?>
        <div class="alert alert-danger">
            <ul><?= $err ?></ul>
        </div>
    <?php
    }
    if ($success) {
    ?>
        <div class="alert alert-primary">
            <?= $success ?>
        </div>
    <?php
    }
    ?>
    <form action="" method="POST" enctype="multipart/form-data">
        <div class="form-group">
            <label for="namausaha">Nama Penginapan</label>
            <input type="text" class="form-control" name="namausaha" id="namausaha" value="<?= $namausaha ?>" />
        </div>
        <div class="form-group">
            <?php
            if ($foto_nama_db != '') {
                echo "<img src='$foto_nama_db' width='150'/><br/>";
            }
            ?>
            <label for="foto">Foto</label>
            <input type="file" id="foto" name="foto" />
        </div>
        <div class="form-group">
            <label for="keterangan">Keterangan</label>
            <textarea class="form-control summernote" id="keterangan" name="keterangan"><?= $keterangan ?></textarea>
        </div>
        <div class="form-group">
            <label for="latitud">Latitud</label>
            <input type="text" class="form-control" name="latitud" id="latitud" value="<?= $latitud ?>" />
        </div>
        <div class="form-group">
            <label for="longitud">Longitud</label>
            <input type="text" class="form-control" name="longitud" id="longitud" value="<?= $longitud ?>" />
        </div>
        <input type="submit" name="simpan" value="Simpan Data" class="btn btn-primary" />
    </form>
<?php
}

require_once("admin_header.php");
$idlogin = $_SESSION['idlogin'];
$op = isset($_GET['op']) ? $_GET['op'] : '';
switch ($op) {
    case '':
        normal();
        break;
    case 'tambah':
        tambah();
        break;
    default:
        normal();
        break;
}
require_once("admin_footer.php");
?>
