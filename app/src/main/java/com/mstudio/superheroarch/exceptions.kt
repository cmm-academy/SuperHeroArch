package com.mstudio.superheroarch

class EpisodeFetchException(episodeUrl: String, message: String) : Exception("Failed to fetch episode: $episodeUrl. $message")
class CharactersFetchException(message: String) : Exception("Failed to fetch characters. $message")