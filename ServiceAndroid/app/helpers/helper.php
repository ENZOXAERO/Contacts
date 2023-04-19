<?php

namespace App\helpers;

/**
 *
 */
class helper{

    /**
     * @param string $value
     * @return mixed
     */
    public static function getConfig(string $value) : mixed {
        $config = require 'config.php';
        return $config[$value];
    }

    /**
     * Returns an array of parameters
     * @param $data
     * @return mixed
     */
    public function parameters($data = null) : mixed {
        parse_str($data, $output);
        return $output;
    }
}