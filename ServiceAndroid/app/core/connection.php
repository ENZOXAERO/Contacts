<?php

namespace App\core;

use \PDO;
use App\helpers\helper;

/**
 *
 */
class connection{

    private static ?PDO $instance = null;

    /**
     * @return PDO|null
     */
    public static function getInstance(): ?PDO {
        $db = helper::getConfig('database');

        self::$instance = new PDO(
            $db['driver'].':host='.$db['host'].';dbname='.$db['dataBase'],$db['user'],
            $db['password'], array(PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES utf8')
        );
        self::$instance->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        return self::$instance;
    }

}

















