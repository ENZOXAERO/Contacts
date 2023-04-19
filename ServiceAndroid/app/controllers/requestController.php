<?php

namespace App\controllers;

use App\core\connection;
use App\helpers\statusErrors;

class requestController{

    private static ?\PDO $con;

    /**
     * @param null $args
     * @return bool|array|string
     */
    public static function contacts($args = null): bool|array|string {
        self::$con = connection::getInstance();
        $query = "select code, name, phone from contacts";
        try {
            $exe = self::$con->prepare($query);
            $exe->execute();
            return array('data' => $exe->fetchAll(2));
        }catch (\Exception $e){
            self::error();
            return $e->getMessage();
        }
    }

    /**
     * @param null $args
     * @return bool|array|string
     */
    public static function save($args = null): bool|array|string {
        self::$con = connection::getInstance();

        $query = "insert into contacts(name, phone) values (:name, :phone)";
        try {
            $exe = self::$con->prepare($query);
            $exe->execute(array(
                ':name' => $args['args0'],
                ':phone' => $args['args1'],
            ));
            return array('data' => array(["response" => "202"]));
        }catch (\Exception $e){
            self::error();
            return array('data' => array(["response" => "404"]));
        }
    }

    /**
     * @param null $args
     * @return bool|array|string
     */
    public static function getContact($args = null): bool|array|string {
        self::$con = connection::getInstance();

        $query = "select code, name, phone from contacts where code = :code";
        try {
            $exe = self::$con->prepare($query);
            $exe->execute(array(
                ':code' => $args['args0']
            ));
            return array('data' => $exe->fetchAll(2));
        }catch (\Exception $e){
            self::error();
            return $e->getMessage();
        }
    }

    /**
     * @param null $args
     * @return bool|array|string
     */
    public static function update($args = null): bool|array|string {
        self::$con = connection::getInstance();

        $query = "update contacts set name = :name, phone = :phone where code = :code";
        try {
            $exe = self::$con->prepare($query);
            $exe->execute(array(
                ':code' => $args['args0'],
                ':name' => $args['args1'],
                ':phone' => $args['args2'],
            ));
            return array('data' => array(["response" => "202"]));
        }catch (\Exception $e){
            self::error();
            return array('data' => array(["response" => "404"]));
        }
    }

    /**
     * @param null $args
     * @return bool|array|string
     */
    public static function delete($args = null): bool|array|string {
        self::$con = connection::getInstance();

        $query = "delete from contacts where code = :code";
        try {
            $exe = self::$con->prepare($query);
            $exe->execute(array(
                ':code' => $args['args0']
            ));
            return array('data' => array(["response" => "202"]));
        }catch (\Exception $e){
            self::error();
            return array('data' => array(["response" => "404"]));
        }
    }

    private static function error(){
        (new statusErrors())->setHeaders('get','404');
    }

}