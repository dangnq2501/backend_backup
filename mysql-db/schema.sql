DROP TABLE IF EXISTS order_list;
DROP TABLE IF EXISTS cart_list;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS role_permission;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS permission;
DROP TABLE IF EXISTS `order`;
DROP TABLE IF EXISTS image;
DROP TABLE IF EXISTS cart_item;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS product;

-- Create table for Permission entity
CREATE TABLE  IF NOT EXISTS permission (
                                           permission_name VARCHAR(255) NOT NULL PRIMARY KEY,  -- Permission name as the primary key
    description VARCHAR(255)  -- Description of the permission
    );

-- Create table for Role entity
CREATE TABLE  IF NOT EXISTS role (
                                     role_name VARCHAR(255) NOT NULL PRIMARY KEY,  -- Role name as the primary key
    description VARCHAR(255)  -- Description of the role
    );

-- Create table for the many-to-many relationship between Role and Permission
CREATE TABLE  IF NOT EXISTS role_permission (
                                                role_name VARCHAR(255) NOT NULL,
    permission_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (role_name, permission_name),
    FOREIGN KEY (role_name) REFERENCES role(role_name) ON DELETE CASCADE,
    FOREIGN KEY (permission_name) REFERENCES permission(permission_name) ON DELETE CASCADE
    );

-- Create table for User entity
CREATE TABLE  IF NOT EXISTS user (
                                     user_id BINARY(16) NOT NULL PRIMARY KEY,  -- UUID stored as binary(16)
    username VARCHAR(255) NOT NULL,  -- Username of the user
    password VARCHAR(255) NOT NULL,  -- Password of the user
    email VARCHAR(255),  -- Email of the user
    phone VARCHAR(50),  -- Phone number of the user
    cart_id BINARY(16)  -- Reference to the user's cart (UUID)
    );

-- Create table for the many-to-many relationship between User and Role
CREATE TABLE  IF NOT EXISTS user_role (
                                          user_id BINARY(16) NOT NULL,
    role_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id, role_name),
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE,
    FOREIGN KEY (role_name) REFERENCES role(role_name) ON DELETE CASCADE
    );

CREATE TABLE  IF NOT EXISTS cart (
                                     cart_id BINARY(16) NOT NULL PRIMARY KEY,  -- UUID stored as BINARY(16)
    total_price DOUBLE  -- Total price of items in the cart
    );
CREATE TABLE  IF NOT EXISTS product (
                                        product_id BINARY(16) NOT NULL PRIMARY KEY,  -- UUID stored as BINARY(16)
    name VARCHAR(255) NOT NULL,  -- Name of the product
    description TEXT,  -- Description of the product
    category VARCHAR(255),  -- Category of the product
    path_id VARCHAR(255),  -- Path identifier
    price DOUBLE,  -- Price of the product
    stock_quantity INT,  -- Quantity in stock
    rating DOUBLE  -- Rating of the product
    );

CREATE TABLE  IF NOT EXISTS cart_item (
                                          cart_item_id BINARY(16) NOT NULL PRIMARY KEY,  -- UUID stored as BINARY(16)
    price DOUBLE,  -- Price of the item
    color VARCHAR(255),  -- Color of the item
    size VARCHAR(50),  -- Size of the item
    quantity INT,  -- Quantity of the item
    product_id BINARY(16),  -- Foreign key to the product table (UUID stored as BINARY(16))
    rating DOUBLE,  -- Rating of the item
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product(product_id)  -- Ensure referential integrity with product
    );



CREATE TABLE  IF NOT EXISTS cart_list (
                                          cart_id BINARY(16) NOT NULL,  -- Foreign key to the cart table
    cart_item_id BINARY(16) NOT NULL,  -- Foreign key to the cart_item table
    PRIMARY KEY (cart_id, cart_item_id),
    CONSTRAINT fk_cart_list_cart FOREIGN KEY (cart_id) REFERENCES cart(cart_id),
    CONSTRAINT fk_cart_list_cart_item FOREIGN KEY (cart_item_id) REFERENCES cart_item(cart_item_id)
    );


CREATE TABLE  IF NOT EXISTS `order` (
                                        order_id BINARY(16) NOT NULL PRIMARY KEY,  -- UUID stored as BINARY(16)
    address VARCHAR(255),  -- Address for the order delivery
    create_at DATETIME,  -- Timestamp of when the order was created
    paid BOOLEAN,  -- Indicates if the order is paid
    status VARCHAR(50),  -- Status of the order (e.g., pending, shipped)
    cart_id BINARY(16),  -- Foreign key to the cart table (UUID stored as BINARY(16))
    username VARCHAR(255),  -- Username associated with the order
    shipping_cost DOUBLE,  -- Cost of shipping the order
    total_cost DOUBLE,  -- Total cost of the order
    CONSTRAINT fk_cart FOREIGN KEY (cart_id) REFERENCES cart(cart_id)  -- Ensure referential integrity with cart
    );

-- Create table for the many-to-many relationship between User and Order
CREATE TABLE  IF NOT EXISTS order_list (
                                           user_id BINARY(16) NOT NULL,
    order_id BINARY(16) NOT NULL,
    PRIMARY KEY (user_id, order_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE,
    FOREIGN KEY (order_id) REFERENCES `order`(order_id) ON DELETE CASCADE
    );

CREATE TABLE  IF NOT EXISTS image (
                                      id BINARY(16) NOT NULL PRIMARY KEY,  -- UUID stored as BINARY(16)
    path_id VARCHAR(255),  -- Identifier for the path
    path_image VARCHAR(255)  -- Path to the image file
    );