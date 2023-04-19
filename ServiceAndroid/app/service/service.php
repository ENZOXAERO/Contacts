<?php

namespace App\service;

use App\helpers\hash;
use App\requestHandler;

/**
 *
 */
class service extends requestHandler{

    public static string $method = '';
    public static ?array $args = null;

    /**
     * Get method and parameters
     */
    public static function request(){
        $data = self::getParameters();
//        self::$method = (new hash())->getHash($data['method'],1);
        self::$method = $data['method'];
        self::$args = $data['args'];
    }

    /** Call the method of the class and pass the parameters
     * @param $class
     */
    public function callMethod($class){
        header("Access-Control-Allow-Origin: *");
        header('Content-Type: application/json; charset=utf-8');
        header("Access-Control-Allow-Methods: OPTIONS,GET,POST,PUT,DELETE");
        header("Access-Control-Max-Age: 3600");
        header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
        call_user_func_array(array($class, 'callback'), array('args' => array('')));
    }

    /** Encode data to UTF-8
     * @param $data
     * @return mixed
     */
    public static function utf8ConvertData($data) : mixed {
        return $data;
    }

    /** Print the result
     * @param $data
     */
    public static function printResult($data){
        echo json_encode($data);
    }

}
