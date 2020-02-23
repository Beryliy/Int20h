package com.fourcore.scheduled

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.fourcore.data.repository.ChallengeRepository
import com.fourcore.data.repository.UserRepository
import kotlinx.coroutines.coroutineScope
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.lang.Exception

class DeadlineCheckerWorker(
    context: Context,
    workerParms: WorkerParameters
): CoroutineWorker(context, workerParms), KoinComponent{

    private val userRepository: UserRepository by inject()
    private val challengeRepository: ChallengeRepository by inject()

    companion object {
        const val CHALLENGE_ID_KEY = "challenge_id_key"
    }

    override suspend fun doWork(): Result  = coroutineScope {
        val challengeId: String = inputData.getString(CHALLENGE_ID_KEY) ?: return@coroutineScope Result.failure()
        try {
            val challenge = challengeRepository.getActiveChallenge(challengeId)
            userRepository.addUserPoints(challenge.owner, 1)
            userRepository.addUserPoints(challenge.participant, -1)
            /*userRepository.updateUser(challenge.owner.apply { points += 1})
            userRepository.updateUser(challenge.participant.apply { if(points > 0) points -= 1})*/
            challengeRepository.finishChallenge(challenge)
        }catch (e: Exception) {
            val challengePerform = challengeRepository.getchallengePerformOf(challengeId)
            if(challengePerform.score >= 0) {
                userRepository.addUserPoints(challengePerform.challenge.participant, 4)
                //userRepository.updateUser(challengePerform.challenge.participant.apply { points += 4 })
            }else {
                userRepository.addUserPoints(challengePerform.challenge.participant, -1)
                userRepository.addUserPoints(challengePerform.challenge.owner, 1)
                //userRepository.updateUser(challengePerform.challenge.participant.apply { if(points > 0) points -= 1 })
                //userRepository.updateUser(challengePerform.challenge.owner.apply { points += 1 })
            }
            challengeRepository.finishChallengePerform(challengePerform)
        }
        return@coroutineScope Result.success()
    }
}