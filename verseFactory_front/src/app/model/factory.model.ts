export interface Factory {
    id: string;
    userId: string;
    balance: number;
    lastUpdatedAt: Date;
}

export interface Pet {
    id: string;
    name: string;
    rarity: string;
    incomePerSecond: number;
    baseCost: number;
}

export interface FactoryPet {
    id: string;
    petId: string;
    name: string;
    rarity: string;
    incomePerSecond: number;
    baseCost: number;
    acquiredAt?: Date;
}