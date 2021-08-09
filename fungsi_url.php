<?php

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
        } else $base_url = 'https://192.168.43.144/dinporabudpar/imgwisata/';

        if ($parse) {
            $base_url = parse_url($base_url);
            if (isset($base_url['path'])) if ($base_url['path'] == '/') $base_url['path'] = '';
        }

        return $base_url;
    }
}
