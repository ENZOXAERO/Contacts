<?php

namespace App;

use App\helpers\statusErrors;
use JetBrains\PhpStorm\ArrayShape;

/**
 *
 */
class requestHandler extends statusErrors {

    /** Get method and parameters from url
     * @return array
     */
    #[ArrayShape(['method' => "mixed", 'args' => "null"])]
    protected static function getParameters(): array {

        $url = $_SERVER['REQUEST_URI'];
        $parameters = parse_url($url);
        parse_str($parameters['query'], $args);

        return [
            'method' => $args['method'],
            'args' => $args
        ];
    }

}