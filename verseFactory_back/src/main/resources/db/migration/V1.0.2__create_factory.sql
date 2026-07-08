CREATE TABLE versefactory.factory (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL UNIQUE,
    balance decimal DEFAULT 0.0,
    last_updated_at timestamp DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_factory_user FOREIGN KEY (user_id) REFERENCES versefactory.app_user(id)
);

CREATE INDEX idx_factory_user_id ON versefactory.factory(user_id);