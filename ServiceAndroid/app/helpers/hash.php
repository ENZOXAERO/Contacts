<?php

namespace App\helpers;

/**
 *
 */
class hash {

    /**
     * @var string
     */
    public static string $chipper = "aes-256-cbc";

    /**
     * @var string
     */
    public static string $alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * @var string
     */
    public static string $salt = "";

    /**
     * @param string $value
     * @param int $type 0 = encrypt 1 = decrypt
     * @return string
     */
    public function getHash(string $value, int $type) : string {
        return $type === 0 ? $this->encrypt($value) : $this->decrypt($value);
    }

    /**
     * Create Encrypted Data
     *
     * @param string $data
     * @return string
     */
    public static function encrypt(string $data): string {
        if (strlen($data) == 0) {
            throw new \RuntimeException("Data cannot be empty");
        }

        $iv = self::getRandomByte();
        return $iv . "." . base64_encode(openssl_encrypt($data, self::$chipper, self::getSalt(), $options = 0, $iv));
    }

    /**
     * @param string $data
     * @return string
     */
    public static function decrypt(string $data) :string {
        if (strlen($data) == 0) {
            throw new \InvalidArgumentException("Data cannot be empty");
        }

        return openssl_decrypt(base64_decode(self::getEncryptedData($data)[1]), self::$chipper, self::getSalt(), $options = 0, self::getEncryptedData($data)[0]);
    }

    /**
     * @return array
     */
    public static function getCipherMethods(): array {
        return openssl_get_cipher_methods();
    }

    /**
     * @return string
     */
    public static function getRandomByte(): string {
        return substr(str_shuffle(self::$alphabet), 0, self::chipperLength());
    }

    /**
     * @return int
     */
    public static function chipperLength(): int {
        return openssl_cipher_iv_length(self::$chipper);
    }


    /**
     * @param  string $data
     * @return array
     */
    private static function getEncryptedData(string $data): array {
        return explode(".", $data);
    }

    /**
     * @return string
     */
    public static function getSalt(): string {
        return self::$salt;
    }
}