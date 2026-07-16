export interface CreateAccountRequest {
    name: string;
    email: string;
    password: string;
}

export interface CreateAccountFormData {
    username: string;
    email: string;
    password: string;
    confirmPassword: string;
}