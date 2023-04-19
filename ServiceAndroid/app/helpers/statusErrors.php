<?php

namespace App\helpers;

/**
 *
 */
class statusErrors{

    private string $httpVersion = "HTTP/1.1";

    /**
     * @param $type
     * @param $code
     */
    public function setHeaders($type, $code){
        $message = $this->errors($code);
        header("{$this->httpVersion} {$code} {$message}");
        header("Content-Type:{$type}");
    }

    /**
     * @param $code
     * @return string
     */
    private function errors($code) : string {
        $status = array(
            404 => 'Not Found',
            405 => 'Method Not Allowed',
            500 => 'Internal Server Error'
        );
        return $status[$code] ?? $status[500];
    }

}
