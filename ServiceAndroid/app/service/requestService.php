<?php

namespace App\service;

use App\helpers\statusErrors;
use App\controllers\requestController;

/**
 *
 */
class requestService extends service {

    public function __construct() {
        self::request();
        $this->callMethod(__CLASS__);
    }

    /** Call the function of controller
     * @param null $args
     */
    public static function callback($args = null) {
        $method = self::$method;

        if (!is_callable([requestController::class, $method])) {
            (new statusErrors())->setHeaders('get', '404');
            return;
        }

        $data = call_user_func_array(array(requestController::class, $method), array('args' => self::$args));
        $data = self::utf8ConvertData($data);
        self::printResult($data);
    }

}